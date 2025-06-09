import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class SlashPage extends StatelessWidget {
  const SlashPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: CupertinoActivityIndicator(
          radius: 20,
          color: Colors.grey,
        ),
      ),
    );
  }
}