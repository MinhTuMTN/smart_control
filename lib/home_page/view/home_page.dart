import 'package:flutter/material.dart';
import 'package:smart_control/core/constants/app_icons.dart';
import 'package:smart_control/home_page/widgets/home_banner.dart';
import 'package:smart_control/home_page/widgets/home_devices.dart';
import 'package:smart_control/core/models/device.dart';
import 'package:smart_control/shared_widgets/my_app_bar.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    var devices = List.of([
      Device(deviceName: "Air Conditioner", iconPath: AppIcons.airConditioner, status: true),
      Device(deviceName: "Fan", iconPath: AppIcons.fan, status: false),
      Device(deviceName: "Light", iconPath: AppIcons.light, status: true),
      Device(deviceName: "TV", iconPath: AppIcons.smartTV, status: true),
    ]);

    return Scaffold(
        appBar: MyAppBar(),
        body: Container(
          color: Colors.white,
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16),
            child: SingleChildScrollView(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  HomeBanner(),
                  SizedBox(height: 20),
                  HomeDevices(devices: devices,)
                ],
              ),
            ),
          ),
        )
      );
  }
}