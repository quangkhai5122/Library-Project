import java.util.Scanner;
public class sinhVien {
    Scanner sc = new Scanner(System.in);
    private String name;
    private String birth;
    private String className;
    private double gpa;

    sinhVien(){
    }
    sinhVien(String name, String birth, String className, double gpa){
        this.name = name;
        this.birth = birth;
        this.className = className;
        this.gpa = gpa;
    }
    public void nhap() {
        this.name = sc.nextLine();
        this.birth = sc.nextLine();
        this.className = sc.nextLine();
        this.gpa = sc.nextDouble();
    }
    public void in(String id){
        System.out.println(id+" "+this.name+" "+this.birth+" "+this.className+" "+String.format("%.2f",this.gpa));
    }
    public void setBirth(){
        StringBuilder sb = new StringBuilder(this.birth);
        if (sb.charAt(1) == '/')
            sb.insert(0,'0');
        if (sb.charAt(4) == '/')
            sb.insert(3,'0');
        this.birth = sb.toString();
    }
    public void setName(){
        String []a = this.name.split("\\s+");
        String newName = "";
        for (String x : a){
            newName += Character.toUpperCase(x.charAt(0));
            for (int j=1 ; j<x.length() ; j++){
                newName += Character.toLowerCase(x.charAt(j));
            }
            newName += " ";
        }
        this.name = newName.trim();
    }
}
