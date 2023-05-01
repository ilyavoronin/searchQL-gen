package generator.exec

sealed interface ValueObject {
    class String(val v: kotlin.String): ValueObject
    class Int(val v: kotlin.Int): ValueObject
    class Bool(val v: Boolean): ValueObject
}