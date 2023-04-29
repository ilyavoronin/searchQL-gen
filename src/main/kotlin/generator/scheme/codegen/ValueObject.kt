package generator.scheme.codegen


sealed interface ValueObject {
    class String(val v: kotlin.String): ValueObject
    class Int(val v: kotlin.Int): ValueObject
    class Bool(val v: Boolean): ValueObject
}