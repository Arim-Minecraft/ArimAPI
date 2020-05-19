/* 
 * ArimAPI-util
 * Copyright © 2020 Anand Beh <https://www.arim.space>
 * 
 * ArimAPI-util is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ArimAPI-util is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ArimAPI-util. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU General Public License.
 */
package space.arim.api.util.sql;

import lombok.Getter;

/**
 * An executable query wrapping a statement string and arguments to the statement. <br>
 * Immutable and thread safe. <br>
 * <br>
 * Implementations of {@link SqlBackend} will create a PreparedStatement from the statement string,
 * then parameters in the statement will be replaced with the arguments provided.
 * 
 * @author A248
 *
 */
@Getter
public class SqlQuery {

	private final String statement;
	private final Object[] args;
	
	/**
	 * Creates from a statement string and arguments to it
	 * 
	 * @param statement the statement string, using question marks where parameters are to be inserted
	 * @param args the arguments to the prepared statement, may be null or empty
	 */
	public SqlQuery(String statement, Object...args) {
		this.statement = statement;
		this.args = args;
	}
	
	/**
	 * Creates from a statement string without any arguments
	 * 
	 * @param statement the statement string; no parameters are possible since no arguments are provided
	 */
	public SqlQuery(String statement) {
		this(statement, (Object[]) null);
	}
	
	/**
	 * Exactly the same as using {@link #SqlQuery(String, Object...)}
	 * 
	 * @param statement the statement string, using question marks where parameters are to be inserted
	 * @param args the arguments to the prepared statement, may be null or empty
	 * @return the query wrapper, never <code>null</code>
	 */
	public static SqlQuery of(String statement, Object...args) {
		return new SqlQuery(statement, args);
	}
	
	/**
	 * Exactly the same as using {@link #SqlQuery(String)}
	 * 
	 * @param statement the statement string; no parameters are possible since no arguments are provided
	 * @return the query wrapper, never <code>null</code>
	 */
	public static SqlQuery of(String statement) {
		return new SqlQuery(statement);
	}
	
}