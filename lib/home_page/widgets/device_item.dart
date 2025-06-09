import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:smart_control/core/models/device.dart';

class DeviceItem extends StatefulWidget {
  final Device device;
  const DeviceItem({super.key, required this.device});

  @override
  State<DeviceItem> createState() => _DeviceItemState();
}

class _DeviceItemState extends State<DeviceItem> {
  @override
  Widget build(BuildContext context) {
    final bool status = widget.device.status;
    void toggle() {
      setState(() {
        widget.device.status = !widget.device.status;
      });
    }

    return Container(
      decoration: BoxDecoration(
        boxShadow: [
          BoxShadow(
            color: Colors.grey,
            offset: Offset(0.0, 1.0), //(x,y)
            blurRadius: 2.0,
          ),
        ],
        borderRadius: BorderRadius.circular(35),
        color: status ? Colors.black : const Color.fromRGBO(246, 248, 252, 1),
      ),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.end,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Expanded(
              flex: 1,
              child: Container(
                alignment: Alignment(-1, 0),
                child: Image.asset(
                  widget.device.iconPath,
                  height: 65,
                  color: status ? Colors.white : Colors.grey[800],
                ),
              ),
            ),
            Expanded(
              flex: 1,
              child: Container(
                alignment: Alignment(1, 1),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Expanded(
                      flex: 1,
                      child: Text(
                        widget.device.deviceName,
                        style: TextStyle(
                          fontSize: 15,
                          color: status ? Colors.white : Colors.grey[800],
                          fontWeight: FontWeight.w700,
                        ),
                        overflow: TextOverflow.clip,
                        textAlign: TextAlign.start,
                      ),
                    ),
                    Padding(
                      padding: const EdgeInsets.only(bottom: 10),
                      child: Transform.rotate(
                        angle: - pi / 2,
                        child: CupertinoSwitch(
                          activeTrackColor: Colors.white,
                          thumbColor: Colors.black,
                          inactiveThumbColor: Colors.grey[800],
                          value: widget.device.status,
                          onChanged: (value) {
                            toggle();
                          },
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
