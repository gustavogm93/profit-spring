package dev.abel.springbootdocker.scraping.country.infrastructure;

import dev.abel.springbootdocker.collections.country.CountryDTO;
import dev.abel.springbootdocker.scraping.country.domain.DataSource;

import java.util.List;

public interface DataSourceService {

    public List<DataSource> getAll();

    public void add(DataSource dataSource);

    public void addAll(List<DataSource> dataSource);

    public List<DataSource> findByTitle(String title);

    public void normalizeDataSource();
}
