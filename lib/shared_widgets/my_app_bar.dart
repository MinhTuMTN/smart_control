import 'package:flutter/material.dart';
import 'package:smart_control/core/constants/app_icons.dart';

class MyAppBar extends StatelessWidget implements PreferredSizeWidget {
  const MyAppBar({super.key});

  @override
  Widget build(BuildContext context) {
    return AppBar(
      leading: Image.asset(
        AppIcons.menu,
        height: 45,
        color: Colors.grey[800],
      ),
      actions: [
        CircleAvatar(
          radius: 22.5,
          backgroundImage: AssetImage(AppIcons.avatar),
        ),
      ],
      title: Text(
        "Smart Control",
        style: TextStyle(
          color: Colors.grey[800],
          fontSize: 20,
          fontWeight: FontWeight.w700
        )
      ),
      backgroundColor: Colors.white,
      surfaceTintColor: Colors.transparent,
    );
  }
  
  @override
  Size get preferredSize => const Size.fromHeight(kToolbarHeight);
}
