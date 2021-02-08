package tasks;

import interactions.PostLog;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RegisterUserTask implements Task {

    private final Object userInfo;

    public RegisterUserTask(Object userInfo) {
        this.userInfo = userInfo;
    }

    public static Performable withInfo(Object userInfo){
        return instrumented(RegisterUserTask.class,userInfo);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                PostLog.to("/register").with(
                        requestSpecification -> requestSpecification.body(userInfo)
                )
        );
    }
}
