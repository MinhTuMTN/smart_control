import 'package:flutter/foundation.dart';
import 'package:smart_control/core/constants/auth_status.dart';
import 'package:smart_control/core/repository/auth_repository.dart';
import 'package:smart_control/login_page/models/user_model.dart';

class AuthViewModel extends ChangeNotifier {
  bool _loading = false;
  bool get loading => _loading;

  UserModel? _user;
  UserModel? get user => _user;

  AuthStatus _authStatus = AuthStatus.unauthenticated;
  AuthStatus get authStatus => _authStatus;


  final AuthRepository _authRepository = AuthRepository();


  AuthViewModel() {
    _checkIsLoggedIn();
  }

  void _checkIsLoggedIn() async {
    _loading = true;
    notifyListeners();

    try {
      final isLoggedIn = await _authRepository.isLoggedIn();
      if (isLoggedIn) {
        _authStatus = AuthStatus.authenticated;
      } else {
        _authStatus = AuthStatus.unauthenticated;
      }
    } catch (e) {
      _authStatus = AuthStatus.unauthenticated;
    } finally {
      _loading = false;
      notifyListeners();
    }
  }

  void login(String email, String password) async {
    _loading = true;
    notifyListeners();

    try {
      UserModel? userModel = await _authRepository.login(email, password);
      if (userModel != null) {
        _user = userModel;
        _authStatus = AuthStatus.authenticated;
      } else {
        _authStatus = AuthStatus.unauthenticated;
      }
    } catch (e) {
      _authStatus = AuthStatus.unauthenticated;
    } finally {
      _loading = false;
      notifyListeners();
    }
  }

  void logout() async {
    _loading = true;
    notifyListeners();

    try {
      await _authRepository.logout();
      _user = null;
      _authStatus = AuthStatus.unauthenticated;
    } catch (e) {
      _authStatus = AuthStatus.authenticated;
    } finally {
      _loading = false;
      notifyListeners();
    }
  
  }

}