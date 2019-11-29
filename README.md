# Компилятор

### Описание языка в форме Бэкуса-Наура

<Программа> ::= <Объявление переменных> <Описание вычислений><br>
<Объявление переменных> ::= Integer <Список переменных><br>
<Список переменных> ::= <Идент>; | <Идент> , <Список переменных><br>
<Описание вычислений> ::= Begin <Список присваиваний> End <br>
<Список присваиваний>::= <Присваивание> | <Присваивание> <Список присваиваний><br>
<Присваивание> ::= <Идент> := <Выражение> ;<br>
<Выражение> ::= <Ун.оп.> <Подвыражение> | <Подвыражение><br>
<Подвыражение> :: = ( <Выражение> ) | <Операнд> | < Подвыражение > <Бин.оп.> <Подвыражение><br>
<Ун.оп.> ::= "-"<br>
<Бин.оп.> ::= "-" | "+" | "*" | "/" <br>
<Операнд> ::= <Идент> | <Const><br>
<Идент> ::= <Буква> <Идент> | <Буква><br>
<Константа> ::= <Цифра> <Константа> | <Цифра><br> 

### Классы лексем

<h5>
<table>
  <tr>
    <th>Название класса лексем</th>
    <th>Пояснение</th>
  </tr>
  <tr><td>KWORD</td><td>Ключевые слова Integer, Begin, End</td></tr>
  <tr><td>IDENT</td><td>Идентификаторы (из строчных латинских букв)</td></tr>
  <tr><td>CONST</td><td>Константы (из цифр)</td></tr>
  <tr><td>DELIM</td><td>Разделители (, ; ( ) )</td></tr>
  <tr><td>OPERH</td><td>Знаки арифметических операций высокого приоритета (* /)</td></tr>
  <tr><td>OPERL</td><td>Знаки арифметических операций низкого приоритета (+ -)</td></tr>
  <tr><td>ASGN</td><td>Знак присваивания (:=)</td></tr>  
</table>
</h5>

### Синтаксический граф
  
![](https://github.com/SarichevAV/compiler/blob/master/illustrations/1.png)
![](https://github.com/SarichevAV/compiler/blob/master/illustrations/2.png)
![](https://github.com/SarichevAV/compiler/blob/master/illustrations/3.png)
![](https://github.com/SarichevAV/compiler/blob/master/illustrations/4.png)
![](https://github.com/SarichevAV/compiler/blob/master/illustrations/5.png)
![](https://github.com/SarichevAV/compiler/blob/master/illustrations/6.png)
![](https://github.com/SarichevAV/compiler/blob/master/illustrations/7.png)





