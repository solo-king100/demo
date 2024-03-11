package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import lombok.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {
    public static void main(String[] args) {


        List<Person> persons= Arrays.asList(
                new Person("Harry",19),
                new Person("anastasia",33),
                new Person("Erik",32)
        );

        //HashMap<String,Integer> map=new HashMap<>();
        //map.put("Harry",19);
        //map.put("Anastasia",33);
        //map.put("Erik",32);


        ObjectMapper om= new ObjectMapper();

        Javalin app = Javalin.create().start(7007);
        //app.get("/", ctx -> ctx.result("Hello World"));

       // app.get("/", ctx-> ctx.json(persons));



        /*app.get("/person/{name}",ctx ->{String name= ctx.pathParam("name");
        Person p= persons.stream().filter(person->person.getName().equals(name)).findFirst().get();
        ctx.json(p)

    });*/

        app.routes(
                ()->{
                    path("person",()->{
                        get("/", ctx ->ctx.json(persons));
                        get("/{name}",ctx->{
                            String name =ctx.pathParam("name");
                            Person p= persons.stream().filter(person->person.getName().equals(name)).findFirst().get();
                            ctx.json(p);
                        });
                        post("/",ctx->{
                            Person incoming=ctx.bodyAsClass(Person.class);
                            ctx.json(incoming);
                        });
                    });
                }
        );

        app.get("/date", ctx ->ctx.json(om.createObjectNode()
                .put("current_date",java.time.LocalDate.now().toString())));


    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @EqualsAndHashCode
    private static class Person{
        String name;
        int age;


    }

    /*@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @EqualsAndHashCode
    private static class LocalDateWrapper{
        private LocalDate date;


        public LocalDateWrapper(int year, int month, int day) {
            this.date = LocalDate.of(year, month, day);
        }


    }*/
}