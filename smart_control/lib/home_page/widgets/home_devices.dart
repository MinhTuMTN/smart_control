import 'package:flutter/material.dart';
import 'package:smart_control/home_page/widgets/device_item.dart';
import 'package:smart_control/core/models/device.dart';

class HomeDevices extends StatelessWidget {
  final List<Device> devices;
  const HomeDevices({super.key, required this.devices});

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.start,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          "Smart Home Devices",
          style: TextStyle(
            fontSize: 22,
            fontWeight: FontWeight.w700
          ),
          textAlign: TextAlign.start,
        ),
        SizedBox(height: 10,),
        GridView.builder(
          shrinkWrap: true, // Added to allow GridView to size itself
          physics: NeverScrollableScrollPhysics(), // Keeps scrolling handled by parent
          gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
            maxCrossAxisExtent: 225,
            mainAxisExtent: 225,
            mainAxisSpacing: 10,
            crossAxisSpacing: 10,
            childAspectRatio: 1 / 10
          ),
          
          itemCount: devices.length,
          itemBuilder: (context, index) => DeviceItem(device: devices[index])
        ),
      ],
    );
  }
}