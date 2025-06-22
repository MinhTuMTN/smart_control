import 'dart:async';
import 'package:go_router/go_router.dart';
import 'package:smart_control/core/constants/auth_status.dart';
import 'package:smart_control/home_page/view/home_page.dart';
import 'package:smart_control/login_page/view/login_page.dart';
import 'package:smart_control/login_page/view_models/auth_view_model.dart';
import 'package:smart_control/routes/routes.dart';
import 'package:smart_control/splash_page/view/slash_page.dart';

class AppRouter {
  final AuthViewModel _authViewModel;

  AppRouter(this._authViewModel);


  GoRouter appRouter() => GoRouter(
    refreshListenable: _authViewModel,
    routes: [
      GoRoute(
        path: Routes.SLASH_PAGE,
        builder: (context, state) => const SlashPage(),
      ),
      GoRoute(
        path: Routes.HOME_PAGE,
        builder: (context, state) => const HomePage(),
      ),
      GoRoute(
        path: Routes.LOGIN_PAGE,
        builder: (context, state) => const LoginPage(),
      ),
    ],
    redirect: redirect,
    debugLogDiagnostics: true,
    initialLocation: Routes.SLASH_PAGE,
  );

  FutureOr<String?> redirect(context, state) async {
    if (_authViewModel.loading) {
      return null;
    }

    final authStatus = _authViewModel.authStatus;
    final isSplash = state.matchedLocation == Routes.SLASH_PAGE;
    final isLogin = state.matchedLocation == Routes.LOGIN_PAGE;


    if (authStatus == AuthStatus.unauthenticated && isSplash) return Routes.LOGIN_PAGE;

    if (authStatus == AuthStatus.authenticated && (isSplash || isLogin)) return Routes.HOME_PAGE;

    if (authStatus == AuthStatus.unauthenticated && !isSplash && !isLogin) return Routes.LOGIN_PAGE;

    return null;
    }
}