import java.util.HashMap;
import java.util.Scanner;

// Parent class
class User {
    protected String username;
    protected String password;
    protected String post;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.post = "";
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPost() {
        return post;
    }
}

// Child class
class UserManager {
    private static HashMap<String, User> users = new HashMap<>();

    public static boolean register(String username, String password) {
        if (username.length() > 12 || password.length() > 12) {
            System.out.println("Error: Max 12 characters for username/password.");
            return false;
        }
        if (users.containsKey(username)) {
            System.out.println("Error: Username already exists!");
            return false;
        }
        users.put(username, new User(username, password));
        System.out.println("Registration successful!");
        return true;
    }

    public static boolean updatePassword(String username, String oldPassword, String newPassword) {
        User user = users.get(username);
        if (user != null && user.authenticate(oldPassword)) {
            user.setPassword(newPassword);
            System.out.println("Password updated.");
            return true;
        }
        System.out.println("Invalid credentials.");
        return false;
    }

    public static boolean deleteUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.authenticate(password)) {
            users.remove(username);
            System.out.println("User deleted.");
            return true;
        }
        System.out.println("Invalid credentials.");
        return false;
    }

    public static boolean postContent(String username, String password, String content) {
        User user = users.get(username);
        if (user != null && user.authenticate(password)) {
            user.setPost(content);
            System.out.println("Post saved.");
            return true;
        }
        System.out.println("Invalid credentials.");
        return false;
    }

    public static void showAllPosts() {
        if (users.isEmpty()) {
            System.out.println("No users yet.");
            return;
        }
        for (User user : users.values()) {
            System.out.println(user.getUsername() + " says: " + user.getPost());
        }
    }
}

// Main class
public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n===== Secure Authentication System =====");
            System.out.println("1. Register");
            System.out.println("2. Update Password");
            System.out.println("3. Delete Account");
            System.out.println("4. Post Content");
            System.out.println("5. Show Posts");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            String username, password, newPassword, post;

            switch (choice) {
                case 1:
                    System.out.print("Enter username (max 12 chars): ");
                    username = sc.nextLine();
                    System.out.print("Enter password (max 12 chars): ");
                    password = sc.nextLine();
                    UserManager.register(username, password);
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    username = sc.nextLine();
                    System.out.print("Enter old password: ");
                    password = sc.nextLine();
                    System.out.print("Enter new password: ");
                    newPassword = sc.nextLine();
                    UserManager.updatePassword(username, password, newPassword);
                    break;

                case 3:
                    System.out.print("Enter username: ");
                    username = sc.nextLine();
                    System.out.print("Enter password: ");
                    password = sc.nextLine();
                    UserManager.deleteUser(username, password);
                    break;

                case 4:
                    System.out.print("Enter username: ");
                    username = sc.nextLine();
                    System.out.print("Enter password: ");
                    password = sc.nextLine();
                    System.out.print("Enter content to post: ");
                    post = sc.nextLine();
                    UserManager.postContent(username, password, post);
                    break;

                case 5:
                    UserManager.showAllPosts();
                    break;

                case 6:
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        sc.close();
    }
}