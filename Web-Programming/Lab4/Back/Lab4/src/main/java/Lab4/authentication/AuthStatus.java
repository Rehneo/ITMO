package Lab4.authentication;

public enum AuthStatus {
    SUCCESS("Аутентификация прошла успешно"),
    WRONG_PASSWORD("Неправильный пароль"),
    INVALID_TOKEN("Некорректный токен"),
    USER_ALREADY_EXISTS("Пользователь с таким именем уже зарегистрирован"),
    USER_DOES_NOT_EXIST("Пользователя с таким именем не существует"),
    INVALID_CREDENTIALS("Логин или пароль не соответствуют требованиям"),
    UNKNOWN_ERROR("Неизвестная ошибка");


    private final String message;

    AuthStatus(String s) {
        this.message = s;
    }

    public String getMessage(){
        return this.message;
    }
}
