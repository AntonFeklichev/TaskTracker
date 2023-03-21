package entity;

public class Epic extends Task {


    public Epic(String name, String description, int id, TaskStatus status) {
        super(name, description, id, status);
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

