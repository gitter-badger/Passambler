# Passambler
Passambler is a simple general purpose language.

It isn't finished yet, but the language is usable.

## Examples
A simple Hello World program looks like this:

```
using('std.Writeln');

Writeln('Hello World');
```

And a FizzBuzz program is written like this:
```
using('std.Writeln');

for (x : 0..100) {
    if ((x % 15) == 0) {
        Writeln('FizzBuzz');
    } elseif ((x % 3) == 0) {
        Writeln('Fizz');
    } elseif ((x % 5) == 0) {
        Writeln('Buzz');
    }
}
```