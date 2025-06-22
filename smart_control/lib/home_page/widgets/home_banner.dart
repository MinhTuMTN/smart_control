import 'package:flutter/material.dart';
import 'package:smart_control/core/constants/app_icons.dart';

class HomeBanner extends StatelessWidget {
  const HomeBanner({super.key});

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              "Welcome back!",
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.w400
              ),
              textAlign: TextAlign.start,
            ),
            SizedBox(height: 5,),
            Text(
              "Minh Tu",
              style: TextStyle(
                fontSize: 25,
                fontWeight: FontWeight.w700
              ),
              textAlign: TextAlign.start,
            )
          ],
        ),
        SizedBox(width: 30,),
        Expanded(
          child: Container(
            alignment: Alignment(1, 0),
            child: Image.asset(
            AppIcons.homeBanner,
            height: 200,  
          ),
          )
        ),
      ],
    );
  }
}