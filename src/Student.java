public class Student {
    private int id;
    private String name;
    private String course;
    private int age;
private String password;
    public Student( int id, String name, String course, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }


    //Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
    public String getCourse(){
        return course;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public void setCourse(String course){
        this.course = course;
    }
@Override
    public  String toString(){
        return id + " | " + name + " | " + course + " | " + age;
}
}