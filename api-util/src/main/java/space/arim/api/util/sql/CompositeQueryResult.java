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

import java.sql.SQLException;

/**
 * Represents the results created from a single query with multiple statements.
 * 
 * @author A248
 *
 */
public interface CompositeQueryResult extends AutoCloseable {

	/**
	 * Gets the next QueryResult and moves the cursor forward. Will return
	 * the first QueryResult if this has never been called before.
	 * 
	 * @return the next query result
	 * @throws SQLException if something went wrong SQL wise
	 */
	QueryResult next() throws SQLException;
	
	/**
	 * Skips the specified amount of query results. This is a shortcut
	 * instead of calling {@link #next()} merely to skip unneeded results.
	 * 
	 * @param skip the amount of results to skip
	 * @throws SQLException if something went wrong SQL wise
	 */
	void skip(int skip) throws SQLException;
	
	/**
	 * Releases any underlying resources.
	 * 
	 * @throws SQLException if something went wrong SQL wise
	 */
	@Override
	void close() throws SQLException;
	
}