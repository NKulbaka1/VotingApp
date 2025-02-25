package model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Vote {
    private String name;
    private String description;
    private Long NumberOfOptions;
    private Map<String, Integer> options;

    public Vote() {
        this.options = new ConcurrentHashMap<>();
    }

    public Vote(String name, String description) {
        this.name = name;
        this.description = description;
        this.options = new ConcurrentHashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getNumberOfOptions() {
        return NumberOfOptions;
    }

    public void setNumberOfOptions(Long numberOfOptions) {
        NumberOfOptions = numberOfOptions;
    }

    public Map<String, Integer> getOptions() {
        return options;
    }

    public void addOption(String option) {
        options.put(option, 0);
    }

    public void voteForOption(String option) {
        options.put(option, options.getOrDefault(option, 0) + 1);
    }
}
