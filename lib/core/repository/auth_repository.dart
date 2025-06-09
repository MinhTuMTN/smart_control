import 'package:smart_control/login_page/models/user_model.dart';

class AuthRepository {

  Future<UserModel?> login(String email, String password) async {
    await Future.delayed(const Duration(seconds: 10));
    if (email == "admin@gmail.com" && password == "admin") {
      return UserModel(
        id: "1", 
        email: email, 
        fullName: "Admin Nguyen"
      );
    } else {
      return null;
    }
  }

  Future<bool> isLoggedIn() async {
    await Future.delayed(const Duration(seconds: 60));
    return false;
  }
}