package entity;

import java.time.LocalDateTime;

public class Epic extends Task {


    public Epic(String name, String description, int id, TaskStatus status) {
        super(name, description, id, status);
    }

    @Override
    public LocalDateTime getEndTime() {
        return super.endTime;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}

