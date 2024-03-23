package main.java.com.sangarius.multithreadedworkerdatabaseprocessing.model.entities;

public class Worker {

    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String education;
    private int experience;
    private int salary;
    private int hoursWorked;

    public Worker(int id, String firstName, String lastName, int age, String gender,
        String education, int experience, int salary, int hoursWorked) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.education = education;
        this.experience = experience;
        this.salary = salary;
        this.hoursWorked = hoursWorked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
