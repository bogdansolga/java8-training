package com.sg.java8.training.optional.without.flatmap;

import com.sg.java8.training.optional.without.flatmap.model.Address;
import com.sg.java8.training.optional.without.flatmap.model.PostalCode;
import com.sg.java8.training.optional.without.flatmap.model.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OldSchoolWay {

    public static void main(String[] args) {

        List<Student> students = setUp();

        //Do I have any student with a temporary address abroad ?

        for (Student student : students) {
            if (student.getTemporaryAddress() != null) {
                Address temporaryAddress = student.getTemporaryAddress();
                if (temporaryAddress.getPostalCode() != null) {
                    PostalCode code = temporaryAddress.getPostalCode();
                    if (!code.getPrefix().equals("RO"))
                        System.out.println("The postal code is : " + code.getPrefix() + "" + code.getNumber());
                }
            }
        }

    }

    private static List<Student> setUp() {
        PostalCode roCode = new PostalCode(10011, "RO");
        PostalCode abroadCode = new PostalCode(10011, "UK");

        Address addressWithPostalCode = new Address(1, "Main Street", "Romania", "Bucharest");
        addressWithPostalCode.setPostalCode(abroadCode);

        Address addressWithoutPostalCode = new Address(2, "Secondary Street", "Romania", "Pitesti");

        Student mihaiFromBucharest = new Student("Mihai", addressWithPostalCode);

        //Mihaela has a temporary address because she is studying abroad
        Student mihaelaFromPitesti = new Student("Mihaela", addressWithoutPostalCode);
        mihaelaFromPitesti.setTemporaryAddress(addressWithPostalCode);

        return new ArrayList<>(Arrays.asList(new Student[]{mihaelaFromPitesti, mihaiFromBucharest}));
    }
}
