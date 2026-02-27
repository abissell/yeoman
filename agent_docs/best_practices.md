# Best Practices

* Timestamps always should be immediately converted to, and stored in, UTC timezone LocalDateTime
* Prefer modern Java idioms including records, record patterns and pattern matching, sealed/final, etc
* Use `var` when the type is obvious from the right-hand side (e.g. constructors, literals, factory methods whose name includes the type). Use an explicit type when the RHS is a method call whose return type isn't immediately clear to a reader — e.g. generic methods like `someFoo.getBar()`, or calls where the return type differs from what the method name suggests
* Schedule independent/parallel threads of work using virtual threads so that we enjoy async benefits
