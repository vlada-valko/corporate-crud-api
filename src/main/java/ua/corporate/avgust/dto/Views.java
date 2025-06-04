package ua.corporate.avgust.dto;

public class Views {
    public static class SecretViews extends AdminViews{}
    public static class AdminViews extends ManagerViews {}
    public static class ManagerViews extends UserViews {}
    public static class UserViews {}
}
