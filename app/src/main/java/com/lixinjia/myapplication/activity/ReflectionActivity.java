package com.lixinjia.myapplication.activity;

import android.app.Person;
import android.view.View;
import android.widget.TextView;

import com.lixinjia.myapplication.R;
import com.lixinjia.myapplication.annotation.TaseAnnotation;
import com.lixinjia.myapplication.text.Text;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;

/**
 * 作者：忻佳
 * 日期：2019-10-24
 * 描述：反射
 */
public class ReflectionActivity extends BaseActivity {
    private TextView textView;
    private StringBuffer sb = new StringBuffer();
    @Override
    public int bindLayout() {
        return R.layout.act_reflection;
    }

    @Override
    public void initView(View view) {
        mTitle.setCenterText("反射");
        textView = $(R.id.act_reflection_tv);
    }

    @Override
    public void doBusiness() {
        Class<Text> aClass = Text.class;
        Package aPackage = aClass.getPackage();
        sb.append("获取完整的类名").append("\n");
        sb.append("getName():").append(aClass.getName()).append("\n");
        sb.append("\n");
        sb.append("仅获取类名").append("\n");
        sb.append("getSimpleName():").append(aClass.getSimpleName()).append("\n");
        sb.append("\n");
        sb.append("获取包名").append("\n");
        assert aPackage != null;
        sb.append("getPackage().getName():").append(aPackage.getName()).append("\n");
        sb.append("\n");
        sb.append("获取普通函数的父类Class对象").append("\n");
        sb.append("getSuperclass():").append(Objects.requireNonNull(aClass.getSuperclass()).toString()).append("\n");
        sb.append("\n");
        sb.append("针对泛型父类而设计").append("\n");
        sb.append("getGenericSuperclass():").append(Objects.requireNonNull(aClass.getGenericSuperclass()).toString()).append("\n");
        sb.append("\n");
        sb.append("获取普通接口的方法").append("\n");
        sb.append("getInterfaces():").append(Arrays.toString(Objects.requireNonNull(TaseAnnotation.class.getInterfaces()))).append("\n");
        sb.append("\n");
        sb.append("获取泛型接口的方法").append("\n");
        sb.append("getGenericInterfaces():").append(Arrays.toString(new String[]{Arrays.toString(TaseAnnotation.class.getGenericInterfaces())})).append("\n");
        sb.append("\n");
        sb.append("获取修饰符").append("\n");
        int modifiers = Text.class.getModifiers();
        sb.append("getModifiers():").append(modifiers).append("\n");
        sb.append("Modifier.toString:").append(Modifier.toString(modifiers)).append("\n");
        sb.append("Modifier.isFinal:").append(Modifier.isFinal(modifiers)).append("\n");
        sb.append("//这个函数的作用就是根据传进来的整型，根据其中的标识位来判断具有哪个修饰符，然后将所有修饰符拼接起来输出。\n" +
                "String Modifier.toString(int modifiers)   \n" +
                "//以下这些方法来检查特定的修饰符是否存在  \n" +
                "boolean Modifier.isAbstract(int modifiers)  \n" +
                "boolean Modifier.isFinal(int modifiers)  \n" +
                "boolean Modifier.isInterface(int modifiers)  \n" +
                "boolean Modifier.isNative(int modifiers)  \n" +
                "boolean Modifier.isPrivate(int modifiers)  \n" +
                "boolean Modifier.isProtected(int modifiers)  \n" +
                "boolean Modifier.isPublic(int modifiers)  \n" +
                "boolean Modifier.isStatic(int modifiers)  \n" +
                "boolean Modifier.isStrict(int modifiers)  \n" +
                "boolean Modifier.isSynchronized(int modifiers)  \n" +
                "boolean Modifier.isTransient(int modifiers)  \n" +
                "boolean Modifier.isVolatile(int modifiers)").append("\n");
        sb.append("\n");
        sb.append("获取类的构造函数").append("\n");
        Constructor<?>[] constructors = Text.class.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : constructors) {
            sb.append("getDeclaredConstructors():").append(declaredConstructor.toString()).append("\n");
        }
        try {
            Constructor<?> constructor = Text.class.getDeclaredConstructor(int.class, String.class);
            sb.append("getDeclaredConstructor() 指定参数得到的构造函数：").append(constructor.toString()).append("\n");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            sb.append("异常：").append(e.getMessage()).append("\n");
        }
        sb.append("\n");
        sb.append("使用获取到的构造函数Constructor构造实例").append("\n");
        try {
            Constructor<Text> constructor = Text.class.getDeclaredConstructor(int.class, String.class);
            //是否将任何函数或字段设置为可访问的。
            //如果设置为true，就不管这个函数或者字段是声明为private还是public，都是可以访问的，默认情况下是false，即只有public类型是可访问的。
            //如果没有设置setAccessible(true)的话，在使用protected或者private构造函数创建实例时，会提示：（访问拒绝）
            constructor.setAccessible(true);
            //构造实例
            Text text =  constructor.newInstance(30,"harvic");
            sb.append("newInstance() 构造的参数为：").append(text.getIndex()).append("  ").append(text.getStr()).append("\n");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            sb.append("异常：").append(e.getMessage()).append("\n");
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        sb.append("\n");
        sb.append("得到获取到的构造函数Constructor的参数类型列表").append("\n");
        for (Constructor<?> c:constructors){
            c.setAccessible(true);
            Class<?>[] types = c.getParameterTypes();

            StringBuilder builder = new StringBuilder("getParameterTypes() 获取参数类型为：");
            for(Class t:types){
                builder.append(t.getName());
                builder.append("   ");
            }
            sb.append(builder.toString()).append("\n");
        }
        sb.append("\n");
        sb.append("得到获取到的构造函数Constructor的访问修饰符").append("\n");
        for (Constructor<?> c:constructors){
            c.setAccessible(true);
            int modifier = c.getModifiers();
            sb.append("getModifiers() 一个访问修饰符为：").append(Modifier.toString(modifier)).append("\n");
        }
        sb.append("\n");
        sb.append("得到声明Constructor的类的Class对象").append("\n");
        try {
            Constructor<?> constructor = Text.class.getDeclaredConstructor();
            Class<?> declarClazz = constructor.getDeclaringClass();
            sb.append("getDeclaringClass()：").append(declarClazz.getName()).append("\n");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            sb.append("异常：").append(e.getMessage()).append("\n");
        }
        sb.append("\n");
        sb.append("获取Field对象").append("\n");
        Field[] fields = Text.class.getDeclaredFields();
        for (Field field:fields){
            field.setAccessible(true);
            Class<?> type = field.getType();
            //field.getName()：用于得到当前成员变量的名称，比如Person类中的age,name
            //field.getType()：用于得到当前成员变量的类型。
            sb.append("getDeclaredFields()：枚举到的field:").append(type.getName()).append("  ").append(field.getName()).append("\n");
        }
        sb.append("\n");
        sb.append("Field的get、set操作").append("\n");
        try {
            Constructor<Text> constructor = Text.class.getDeclaredConstructor();
            Text person = constructor.newInstance();

            Field fStr = aClass.getDeclaredField("str");
            fStr.setAccessible(true);
            fStr.set(person, "qijian");
            String val = (String)fStr.get(person);
            sb.append("fieldName:").append(val).append("   str:").append(person.getStr()).append("\n");

            Field fIndex = aClass.getDeclaredField("index");
            fIndex.setAccessible(true);
            fIndex.setInt(person, 20);
            sb.append("fieldIndex:").append(fIndex.getInt(person)).append("   index:").append(person.getIndex()).append("\n");
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
            sb.append("异常：").append(e.getMessage()).append("\n");
        }
        sb.append("\n");
        sb.append("获取该成员变量所对应的访问修饰符组所对应的Int数字  ").append("\n");
        try {
            Field fIndex = aClass.getDeclaredField("index");
            Class<?> declareClazz = fIndex.getDeclaringClass();
            sb.append("getDeclaringClass:").append(declareClazz.toString()).append("\n");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            sb.append("异常：").append(e.getMessage()).append("\n");
        }
        sb.append("\n");
        sb.append("Method之Invoke").append("\n");
        try {
            Class<?> clazz = Text.class;
            Text text = new Text();
            //public Boolean testInvoke(Integer age,String name)
            Method method = clazz.getDeclaredMethod("testInvoke", int.class,String.class);

            method.setAccessible(true);
            String result = (String)method.invoke(text, 25, "I m harvic");
            sb.append("执行结果:").append(result).append("\n");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            sb.append("异常：").append(e.getMessage()).append("\n");
        }
        sb.append("\n");
        sb.append("Method之获取参数类型").append("\n");
        try {
            Class<?> clazz = Text.class;
            Method method = clazz.getDeclaredMethod("testInvoke", int.class,String.class);
            Class<?>[] params = method.getParameterTypes();
            for (Class c:params){
                sb.append("枚举到参数类型:").append(c.getName()).append("\n");
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            sb.append("异常：").append(e.getMessage()).append("\n");
        }
        sb.append("\n");
        sb.append("Method之获取返回值类型").append("\n");
        try {
            Class<?> clazz = Text.class;
            Method method = clazz.getDeclaredMethod("testInvoke", int.class,String.class);
            Class type = method.getReturnType();
            sb.append("枚举到参数类型:").append(type.getName()).append("\n");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            sb.append("异常：").append(e.getMessage()).append("\n");
        }

        textView.setText(sb.toString());
    }
}
