package com.yipsuwa.k_app

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Toast
import com.github.fujianlian.klinechart.DataHelper
import com.github.fujianlian.klinechart.KLineChartAdapter
import com.github.fujianlian.klinechart.KLineChartView
import com.github.fujianlian.klinechart.KLineEntity
import com.github.fujianlian.klinechart.draw.Status
import com.github.fujianlian.klinechart.formatter.DateFormatter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView
import java.nio.charset.Charset

class MyFlutterView(val context: Context, messenger: BinaryMessenger, viewId: Int, args: Map<String, Any>?) : PlatformView, MethodChannel.MethodCallHandler {

    val methidChannel = MethodChannel(messenger, "fromflutter_channel")
    val kLineChartView: KLineChartView = KLineChartView(context)
    private val adapter by lazy { KLineChartAdapter() }

    init {
        kLineChartView.adapter = adapter
        kLineChartView.dateTimeFormatter = DateFormatter()
        kLineChartView.setGridRows(4)
        kLineChartView.setGridColumns(4)
        initDataDay()
        initListener()
        methidChannel.setMethodCallHandler(this)
    }

    override fun getView(): View {
        return kLineChartView
    }

    override fun dispose() {
        methidChannel.setMethodCallHandler(null)
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        if (call.method == "androidfunbyflutter") {
            val name = call.argument("name") as String?
            val age = call.argument("age") as Int?

        } else if(call.method == "setShowType"){
            val key = call.argument<String>("key")
            setShowType(key)

        } else {
            result.notImplemented()
        }
    }
    private fun setShowType(key : String?){
        when(key){
            "MA" ->{
                kLineChartView.changeMainDrawType(Status.MA)
            }
            "BOLL" ->{
                kLineChartView.changeMainDrawType(Status.BOLL)
            }
            "隐藏1" ->{
                kLineChartView.changeMainDrawType(Status.NONE)
            }
            "MACD" ->{
                kLineChartView.setChildDraw(0)
            }
            "KDJ" ->{
                kLineChartView.setChildDraw(1)
            }
            "RSI" ->{
                kLineChartView.setChildDraw(2)
            }
            "WR" ->{
                kLineChartView.setChildDraw(3)
            }
            "隐藏2" ->{
                kLineChartView.hideChildDraw()
            }
            "分时" ->{
                kLineChartView.setMainDrawLine(true)
            }
            "日k" ->{
                initDataDay()
                kLineChartView.setMainDrawLine(false)
            }
            "月k" ->{
                initDataMonth()
                kLineChartView.setMainDrawLine(false)
            }
            else ->{
               val toast =  Toast(context)
                toast.setText("暂未实现该方法")
                toast.duration = Toast.LENGTH_SHORT
                toast.show()
            }
        }
    }

    private fun initDataDay() {
        kLineChartView.justShowLoading()
        val data = getALL(context)
        adapter.addFooterData(data)
        adapter.notifyDataSetChanged()
//        kLineChartView.startAnimation()
        kLineChartView.refreshEnd()
    }
    private fun initDataMonth() {
        kLineChartView.justShowLoading()
        val data = getALLMonth(context)
        adapter.addFooterData(data)
        adapter.notifyDataSetChanged()
//        kLineChartView.startAnimation()
        kLineChartView.refreshEnd()
    }

    private fun initListener() {
//        maText.setOnClickListener {
//            if (mainIndex != 0) {
//                kLineChartView.hideSelectData()
//                mainIndex = 0
//                maText.textColor = Color.parseColor("#eeb350")
//                bollText.textColor = Color.WHITE
//                kLineChartView.changeMainDrawType(Status.MA)
//            }
//        }
//        bollText.setOnClickListener {
//            if (mainIndex != 1) {
//                kLineChartView.hideSelectData()
//                mainIndex = 1
//                bollText.textColor = Color.parseColor("#eeb350")
//                maText.textColor = Color.WHITE
//                kLineChartView.changeMainDrawType(Status.BOLL)
//            }
//        }
//        mainHide.setOnClickListener {
//            if (mainIndex != -1) {
//                kLineChartView.hideSelectData()
//                mainIndex = -1
//                bollText.textColor = Color.WHITE
//                maText.textColor = Color.WHITE
//                kLineChartView.changeMainDrawType(Status.NONE)
//            }
//        }
//        for ((index, text) in subTexts.withIndex()) {
//            text.setOnClickListener {
//                if (subIndex != index) {
//                    kLineChartView.hideSelectData()
//                    if (subIndex != -1) {
//                        subTexts[subIndex].textColor = Color.WHITE
//                    }
//                    subIndex = index
//                    text.textColor = Color.parseColor("#eeb350")
//                    kLineChartView.setChildDraw(subIndex)
//                }
//            }
//        }
//        subHide.setOnClickListener {
//            if (subIndex != -1) {
//                kLineChartView.hideSelectData()
//                subTexts[subIndex].textColor = Color.WHITE
//                subIndex = -1
//                kLineChartView.hideChildDraw()
//            }
//        }
//        fenText.setOnClickListener {
//            kLineChartView.hideSelectData()
//            fenText.textColor = Color.parseColor("#eeb350")
//            kText.textColor = Color.WHITE
//            kLineChartView.setMainDrawLine(true)
//        }
//        kText.setOnClickListener {
//            kLineChartView.hideSelectData()
//            kText.textColor = Color.parseColor("#eeb350")
//            fenText.textColor = Color.WHITE
//            kLineChartView.setMainDrawLine(false)
//        }
    }


    fun getALL(context: Context?): List<KLineEntity?>? {
        val data: List<KLineEntity> = Gson().fromJson(getStringFromAssert(context!!, "ibm.json"), object : TypeToken<List<KLineEntity?>?>() {}.getType())
        DataHelper.calculate(data)
        return data

    }
    fun getALLMonth(context: Context?): List<KLineEntity?>? {
        val data: List<KLineEntity> = Gson().fromJson(getStringFromAssert(context!!, "ibm2.json"), object : TypeToken<List<KLineEntity?>?>() {}.getType())
        DataHelper.calculate(data)
        return data

    }

    fun getStringFromAssert(context: Context, fileName: String?): String? {
        try {
            val ins = context.resources.assets.open(fileName!!)
            val length = ins.available()
            val buffer = ByteArray(length)
            ins.read(buffer)
            return String(buffer, 0, buffer.size, Charset.forName("UTF-8"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}