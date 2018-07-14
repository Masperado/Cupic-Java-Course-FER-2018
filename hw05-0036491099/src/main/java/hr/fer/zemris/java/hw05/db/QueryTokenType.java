package hr.fer.zemris.java.hw05.db;

/**
 * This enumeration represents {@link QueryToken} type.
 */
public enum QueryTokenType {

    /**
     * Field type.
     */
    FIELD,

    /**
     * Operator type.
     */
    OPERATOR,

    /**
     * String type.
     */
    STRING,

    /**
     * And type.
     */
    AND,

    /**
     * End of File type.
     */
    EOF

}
