package io.github.zemelua.umu_config.api.config.category;

import io.github.zemelua.umu_config.api.config.IConfigElement;

import java.util.List;

public interface IConfigCategory extends IConfigElement {
	List<IConfigElement> getElements();
}
