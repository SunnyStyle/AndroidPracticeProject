package com.amigo.ai.rxjavatest;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxMainActivity extends AppCompatActivity {

    private final static String tag = "RxMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageView = (ImageView)findViewById(R.id.test_iv);

        /*String[] names = {"Smith","Amy","Linda"};

        rx.Observable.from(names)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String str) {
                        Log.e(tag,"str = " + str);
                    }
                });*/

        Person[] persons = {};
        rx.Observable.from(persons)
                .subscribe(new Action1<Person>() {
                    @Override
                    public void call(Person s) {

                    }
                });

        final int drawables = R.drawable.home_icon_day;
        Subscription subscribe = Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(drawables);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Drawable drawable) {
                imageView.setImageDrawable(drawable);
            }
        });

        testMap();
        testFlatMap();

    }

    private void testMap() {
        Observable.just("images/logo.png")
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String filePath) {
                        //将string 转为 bitmap
                        return null;
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        //use bitmap
                    }
                });
    }

    private void testFlatMap(){
        Student student1 = new Student();
        student1.setName("Amy");

        List<Course> courses = new ArrayList<>();
        Course course1 = new Course();
        course1.setCourseName("English");
        course1.setLevel(95);
        Course course2 = new Course();
        course2.setCourseName("Math");
        course2.setLevel(99);
        courses.add(course1);
        courses.add(course2);

        student1.setCourses(courses);

        Student student2 = new Student();
        student2.setName("Tom");

        List<Course> courses2 = new ArrayList<>();
        Course course21 = new Course();
        course21.setCourseName("English2");
        course21.setLevel(95);
        Course course22 = new Course();
        course22.setCourseName("Math2");
        course22.setLevel(99);
        courses2.add(course21);
        courses2.add(course22);

        student2.setCourses(courses2);

        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);

        Subscriber<Course> subscriber = new Subscriber<Course>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Course course) {
                Log.e(tag,"course = " + course.getCourseName());
            }
        };

        Observable.from(studentList)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        Log.e(tag,"student = " + student.getName());
                        return Observable.from(student.getCourses());
                    }
                })
                .subscribe(subscriber);


    }

}
