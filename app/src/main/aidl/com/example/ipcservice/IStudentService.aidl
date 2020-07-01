// IStudentService.aidl
package com.example.ipcservice;

import com.example.ipcservice.Student;
import java.util.List;

// Declare any non-default types here with import statements

interface IStudentService {
    void addStudent(String id, String name, int age, String gender, String address);
    List<Student> getStudents();
    Student getStudent(String id);
}
