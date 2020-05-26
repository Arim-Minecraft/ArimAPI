/**
 * Classes in this package are used to help implement implement the SQL framework {@link space.arim.api.util.sql}.
 * They mostly consist of simple, boilerplate implementations for closable interfaces. <br>
 * <br>
 * The general form of the name of many implementation classes is one which begins with the implemented interface's name,
 * and ends with "WithPreparedStatement", "WithPreparedStatementAndConnection", or "WithConnection". In any such case,
 * the accompanying objects (of the types described after the "With", such as PreparedStatement) will be closed when
 * the overall interface is closed. These accompanying objects must be passed to the constructor of the implementing class.
 * 
 */
package space.arim.api.util.sql.impl;