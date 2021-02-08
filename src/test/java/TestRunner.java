import facts.NetflixPlans;
import models.users.Datum;
import models.users.RegisterUser;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import questions.GetUsersQuestion;
import questions.ResponseCode;
import tasks.GetUsersTask;
import tasks.RegisterUserTask;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.notNullValue;


@RunWith(SerenityRunner.class)
public class TestRunner {

    public static final String urlWeb = "https://reqres.in/api";
    public static final String urlLocal = "http://localhost:5000/api";


    //Prueba agregando tasks y questions
    @Test
    public void initialTest() {

        Actor lucas = Actor.named("Actor EXD74026").whoCan(CallAnApi.at(urlWeb));

        lucas.attemptsTo(
                GetUsersTask.fromPage(1)
        );

        //Lucas deberia ver (seeThat)
        lucas.should(
                seeThat("el codigo de respuesta", ResponseCode.was(), CoreMatchers.equalTo(200))
        );

        Datum user = new GetUsersQuestion().answeredBy(lucas)
                .getData().stream().filter(x -> x.getId() == 1).findFirst().orElse(null);

        lucas.should(
                seeThat("usuario no es nulo", act -> user, notNullValue())
        );

        /* Si el usuario NO viene nulo corre */
        lucas.should(
                seeThat("el email del usuario", act -> user.getEmail(), CoreMatchers.equalTo("george.bluth@reqres.in")),
                seeThat("el avatar del usuario", act -> user.getAvatar(), CoreMatchers.equalTo("https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg"))
        );

    }

    /* PRUEBA ORIGINAL
    @Test
    public void getUsers(){
        Actor lucas = Actor.named("Actor EXD74026").whoCan(CallAnApi.at(urlRest));
        lucas.attemptsTo(
                Get.resource("/users?page=2")
        );
        //prueba de fallo 400
        assertThat(SerenityRest.lastResponse().statusCode()).isEqualTo(200);
    }*/

    @Test
    public void registerUserTestWithString() {

        Actor lucas = Actor.named("Actor EXD74026").whoCan(CallAnApi.at(urlWeb));

        String registerUserInfo = "{\n" +
                "\t\"name\" : \"marpeheus\",\n" +
                "\t\"job\": \"leader\",\n" +
                "\t\"email\": \"tracey.ramos@regres.in\",\n" +
                "\t\"password\": \"serenity\"\n" +
                "}";

        lucas.attemptsTo(
                RegisterUserTask.withInfo(registerUserInfo)
        );

        lucas.should(
                seeThat("el codigo de respuesta", ResponseCode.was(), CoreMatchers.equalTo(400))
        );

    }

    @Test
    public void registerUserTestWithModel() {

        Actor lucas = Actor.named("Actor EXD74026")
                .whoCan(CallAnApi.at(urlWeb));
        /*
        String registerUserInfo = "{\n" +
                "\t\"name\" : \"marpeheus\",\n" +
                "\t\"job\": \"leader\",\n" +
                "\t\"email\": \"tracey.ramos@regres.in\",\n" +
                "\t\"password\": \"serenity\"\n" +
                "}";
         */
        RegisterUser registerUser = new RegisterUser();
        registerUser.setName("marpeheus");
        registerUser.setJob("leader");
        registerUser.setEmail("eve.holt@reqres.in");
        registerUser.setPassword("serenity");

        lucas.attemptsTo(
                RegisterUserTask.withInfo(registerUser)
        );

        lucas.should(
                seeThat("el codigo de respuesta", ResponseCode.was(), CoreMatchers.equalTo(400))
        );

    }

    @Test
    public void factTest() {

        Actor lucas = Actor.named("Actor EXD74026").whoCan(CallAnApi.at(urlWeb));

        lucas.has(NetflixPlans.toViewsSeries());

        lucas.should(
                seeThat("el codigo de respuesta", ResponseCode.was(), CoreMatchers.equalTo(200))
        );

    }


}
