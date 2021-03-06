/* 
 * ArimAPI-configure
 * Copyright © 2020 Anand Beh <https://www.arim.space>
 * 
 * ArimAPI-configure is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ArimAPI-configure is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ArimAPI-configure. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU General Public License.
 */
package space.arim.api.configure.yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import space.arim.api.configure.ConfigComment;
import space.arim.api.configure.ConfigData;
import space.arim.api.configure.ConfigReadResult;
import space.arim.api.configure.ConfigWriteResult;
import space.arim.api.configure.DefaultResourceProvider;
import space.arim.api.configure.ValueTransformer;
import space.arim.api.configure.impl.AbstractConfigSerialiser;
import space.arim.api.configure.impl.SimpleConfigReadResult;
import space.arim.api.configure.impl.SimpleConfigWriteResult;

/**
 * Configuration serialiser using YAML format. <br>
 * <br>
 * Note that, regarding {@link ConfigReadResult}s, the result of {@link #readConfig(Path, Executor, List)} will never return an exception
 * for a specific implementation (such as SnakeYaml). Instead, the general {@link YamlSyntaxException} will always be used.
 * 
 * @author A248
 *
 * @deprecated See deprecation of {@link space.arim.api.configure} (this entire framework is deprecated)
 */
@Deprecated(forRemoval = true)
public class YamlConfigSerialiser extends AbstractConfigSerialiser {
	
	/**
	 * Creates a serialiser
	 * 
	 */
	public YamlConfigSerialiser() {
		
	}
	
	@Override
	protected ConfigReadResult readConfig0(DefaultResourceProvider defaultResource, List<? extends ValueTransformer> transformers) {
		ConfigData result;
		try (InputStream inputStream = defaultResource.openStream();
				YamlParser parser = new YamlParser(inputStream, transformers)) {
			result = parser.parse();

		} catch (IOException ex) {
			return new SimpleConfigReadResult(ConfigReadResult.ResultType.FAILURE_READING, ex, null);
		} catch (YamlSyntaxException ex) {
			return new SimpleConfigReadResult(ConfigReadResult.ResultType.FAILURE_PARSING, ex, null);
		}
		return new SimpleConfigReadResult(ConfigReadResult.ResultType.SUCCESS, null, result);
	}

	@Override
	protected ConfigReadResult readConfig0(Path source, List<? extends ValueTransformer> transformers) {
		ConfigData result;
		try (YamlParser parser = new YamlParser(source, transformers)) {
			result = parser.parse();

		} catch (IOException ex) {
			return new SimpleConfigReadResult(ConfigReadResult.ResultType.FAILURE_READING, ex, null);
		} catch (YamlSyntaxException ex) {
			return new SimpleConfigReadResult(ConfigReadResult.ResultType.FAILURE_PARSING, ex, null);
		}
		return new SimpleConfigReadResult(ConfigReadResult.ResultType.SUCCESS, null, result);
	}
	
	@Override
	protected ConfigWriteResult writeConfig0(Path target, Map<String, Object> values, Map<String, List<ConfigComment>> comments) {
		try (YamlDumper dumper = new YamlDumper(target)) {
			dumper.dump(values, comments);

		} catch (IOException ex) {
			return new SimpleConfigWriteResult(ConfigWriteResult.ResultType.FAILURE_WRITING, ex);
		}
		return new SimpleConfigWriteResult(ConfigWriteResult.ResultType.SUCCESS, null);
	}	

}
