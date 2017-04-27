package proceduralGeneration;

import java.awt.*;
import java.util.Optional;

public class Room extends Rectangle {
    Optional<Integer> character;

    public Room(int value) {
        character = Optional.of(value);
        printCharacter();
    }

    public Room() {
        character = Optional.empty();
        printCharacter();
    }

    private void printCharacter() {
        if (character.isPresent()) {
            System.out.println("hey, my number is " + character.get());
        } else {
            System.out.println("I don't exist");
        }
    }

    public static void main(String[] args) {
        Room room = new Room();
    }
}
