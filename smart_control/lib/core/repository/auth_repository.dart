import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:smart_control/login_page/models/user_model.dart';

class AuthRepository {
  final storage = FlutterSecureStorage();

  Future<UserModel?> login(String email, String password) async {
    await Future.delayed(const Duration(seconds: 2));
    // print("Login attempt with email: $email, password: $password"); // Log ban đầu, có thể giữ hoặc bỏ
    if (email == "admin@gmail.com" && password == "admin") {
      print("Credentials valid for $email. Attempting to save token...");
      try {
        await storage.write(key: "token", value: "adminToken");
        print("Token 'adminToken' saved successfully for $email.");
        return UserModel(
          id: "1",
          email: email,
          fullName: "Admin Nguyen"
        );
      } catch (e) {
        print("Failed to save token for $email. Error: $e");
        // Nếu không lưu được token, coi như đăng nhập thất bại.
        return null;
      }
    } else {
      print("Invalid credentials for $email.");
      return null;
    }
  }

  Future<void> logout() async {
    await storage.delete(key: "token");
  }

  Future<bool> isLoggedIn() async {
    // await Future.delayed(const Duration(seconds: 10));
    final token = await storage.read(key: "token");
    if (token != null && token == "adminToken") {
      return true;
    }
    return false;
  }
}