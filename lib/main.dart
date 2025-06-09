import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:smart_control/login_page/view_models/auth_view_model.dart';
import 'package:smart_control/routes/app_router.dart';


void main() {
  runApp(const MainApp());
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => AuthViewModel()),
      ],
      child: Builder(
        builder: (context) {
          final authViewModel = context.read<AuthViewModel>();
          final router = AppRouter(authViewModel).appRouter();
          return MaterialApp.router(
            routerConfig: router,
            title: 'Smart Control',
            debugShowCheckedModeBanner: false,
          );
        }
      ),
    );
  }
}
