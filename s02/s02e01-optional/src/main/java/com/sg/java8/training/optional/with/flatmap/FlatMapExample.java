package com.sg.java8.training.optional.with.flatmap;

import com.sg.java8.training.optional.with.flatmap.model.Address;
import com.sg.java8.training.optional.with.flatmap.model.Student;
import com.sg.java8.training.optional.with.flatmap.model.PostalCode;

import java.util.*;


public class FlatMapExample {

    public static void main(String[] args) {

        List<Student> students = setUp();

        //Do I have any student with a temporary address abroad ?

        //With postal code
        students.stream()
                .filter(student -> student.getName().equals("Mihai"))
                .findAny()
                .flatMap(Student::getTemporaryAddress)
                .flatMap(Address::getPostalCode)
                .filter(postalCode -> !postalCode.getPrefix().equals("RO"))
                .ifPresent(code -> System.out.println("The postal code is : " +code.getPrefix() + "" + code.getNumber()));

        //Without postal code
        students.stream()
                .filter(student -> student.getName().equals("Mihaela"))
                .findAny()
                .flatMap(Student::getTemporaryAddress)
                .flatMap(Address::getPostalCode)
                .filter(postalCode -> !postalCode.getPrefix().equals("RO"))
                .ifPresent(code -> System.out.println("The postal code is : " +code.getPrefix() + "" + code.getNumber()));

    }

    private static List<Student> setUp() {
        PostalCode roCode = new PostalCode(10011, "RO");
        PostalCode abroadCode = new PostalCode(10011, "UK");

        Address addressWithPostalCode = new Address(1, "Main Street", "Romania", "Bucharest");
        addressWithPostalCode.setPostalCode(abroadCode);

        Address addressWithoutPostalCode = new Address(2, "Secondary Street", "Romania", "Pitesti");

        Student mihaiFromBucharest = new Student("Mihai", addressWithPostalCode);

        //Mihaela has a temporary address because she is studying in Bucharest
        Student mihaelaFromPitesti = new Student("Mihaela", addressWithoutPostalCode);
        mihaelaFromPitesti.setTemporaryAddress(addressWithPostalCode);

        return new ArrayList<>(Arrays.asList(new Student[]{mihaelaFromPitesti, mihaiFromBucharest}));
    }

}
