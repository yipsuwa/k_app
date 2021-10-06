import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const plateform = MethodChannel("fromflutter_channel");
  int _oneIndex = 0;
  int _twoIndex = 4;
  int _threeIndex = 1;

  void onePress0(){
    plateform.invokeMethod(
        "setShowType", {"key": "MA",});
    setState(() {
      _oneIndex = 0;
    });
  }
  void onePress1(){
    plateform.invokeMethod(
        "setShowType", {"key": "BOLL",});
    setState(() {
      _oneIndex = 1;
    });
  }
  void onePress2(){
    plateform.invokeMethod(
        "setShowType", {"key": "隐藏1",});
    setState(() {
      _oneIndex = 2;
    });
  }
  void twoPress0(){
    plateform.invokeMethod(
        "setShowType", {"key": "MACD",});
    setState(() {
      _twoIndex = 0;
    });
  }
  void twoPress1(){
    plateform.invokeMethod(
        "setShowType", {"key": "KDJ",});
    setState(() {
      _twoIndex = 1;
    });
  }
  void twoPress2(){
    plateform.invokeMethod(
        "setShowType", {"key": "RSI",});
    setState(() {
      _twoIndex = 2;
    });
  }
  void twoPress3(){
    plateform.invokeMethod(
        "setShowType", {"key": "WR",});
    setState(() {
      _twoIndex = 3;
    });
  }
  void twoPress4(){
    plateform.invokeMethod(
        "setShowType", {"key": "隐藏2",});
    setState(() {
      _twoIndex = 4;
    });
  }
  void threePress0(){
    plateform.invokeMethod(
        "setShowType", {"key": "分时",});
    setState(() {
      _threeIndex = 0;
    });
  }
  void threePress1(){
    plateform.invokeMethod(
        "setShowType", {"key": "日k",});
    setState(() {
      _threeIndex = 1;
    });
  }
  void threePress2(){
    plateform.invokeMethod(
        "setShowType", {"key": "月k",});
    setState(() {
      _threeIndex = 2;
    });
  }

  @override
  Widget build(BuildContext context) {
    Map jsonMap = {"111": 111, "222": 2222, "333": 333};
    var jsonBean = json.encode(jsonMap).toString();
    return Scaffold(
      appBar: AppBar(
        toolbarHeight: 50,
        title: const Text("Flutter调用原生View"),
        backgroundColor: Colors.grey[900],
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Container(
              height: 40,
              color: Colors.grey[900],
              child: Row(
                children:  [
                  TextButton(
                    onPressed: onePress0,
                    child: Text("MA", style: TextStyle(fontSize: 14,  color: _oneIndex == 0 ? Colors.orange : Colors.white)),
                  ),
                  TextButton(
                    onPressed: onePress1,
                    child: Text("BOLL", style: TextStyle(fontSize: 14,  color: _oneIndex == 1 ? Colors.orange: Colors.white )),
                  ),
                  TextButton(
                    onPressed: onePress2,
                    child: const Text("隐藏", style: TextStyle(fontSize: 14,  color: Colors.white)),
                  )
                ],
              ),
            ),
            Container(
              height: 40,
              color: Colors.grey[900],
              child: Row(
                children:  [
                  TextButton(
                    onPressed: twoPress0,
                    child: Text("MACD", style: TextStyle(fontSize: 14,  color: _twoIndex == 0 ? Colors.orange : Colors.white)),
                  ),
                  TextButton(
                    onPressed: twoPress1,
                    child: Text("KDJ", style: TextStyle(fontSize: 14,  color: _twoIndex == 1 ? Colors.orange: Colors.white )),
                  ),
                  TextButton(
                    onPressed: twoPress2,
                    child: Text("RSI", style: TextStyle(fontSize: 14,  color: _twoIndex == 2 ? Colors.orange: Colors.white )),
                  ),
                  TextButton(
                    onPressed: twoPress3,
                    child: Text("WR", style: TextStyle(fontSize: 14,  color: _twoIndex == 3 ? Colors.orange: Colors.white )),
                  ),
                  TextButton(
                    onPressed: twoPress4,
                    child: const Text("隐藏", style: TextStyle(fontSize: 14,  color: Colors.white)),
                  )
                ],
              ),
            ),
            Container(
              height: 40,
              color: Colors.grey[900],
              child: Row(
                children:  [
                  TextButton(
                    onPressed: threePress0,
                    child: Text("分时图", style: TextStyle(fontSize: 14,  color: _threeIndex == 0 ? Colors.orange : Colors.white)),
                  ),
                  TextButton(
                    onPressed: threePress1,
                    child: Text("日k", style: TextStyle(fontSize: 14,  color: _threeIndex == 1 ? Colors.orange: Colors.white )),
                  ),
                  TextButton(
                    onPressed: threePress2,
                    child: Text("月k", style: TextStyle(fontSize: 14,  color: _threeIndex == 2 ? Colors.orange: Colors.white )),
                  )
                ],
              ),
            ),
            Container(
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height - 170 - MediaQuery.of(context).padding.top - MediaQuery.of(context).padding.bottom,
              decoration: BoxDecoration(color: Colors.grey[900]),
              child: AndroidView(
                  viewType: 'plugins.flutter.io/custom_platform_view',
                  creationParams: {
                    "key": "i am key",
                    "value": "i am value",
                    "bean": jsonBean
                  },
                  creationParamsCodec: StandardMessageCodec()),
            ),
            // Container(height: 30),
            // Container(
            //     decoration: BoxDecoration(
            //         color: const Color(0xEEEEEEEE),
            //         borderRadius: BorderRadius.circular(8)),
            //     child: TextButton(
            //       onPressed: () {
            //         plateform.invokeMethod(
            //             "androidfunbyflutter", {"name": "ysh", "age": 28});
            //       },
            //       child: const Text(
            //         "传递K线数据给Android原生",
            //         style:
            //             TextStyle(fontSize: 18, color: Colors.lightBlueAccent),
            //       ),
            //     )),
          ],
        ),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
