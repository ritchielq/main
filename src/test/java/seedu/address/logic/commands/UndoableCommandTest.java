package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.junit.Test;

import seedu.address.email.EmailManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

public class UndoableCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new EmailManager());
    private final DummyCommand dummyCommand = new DummyCommand(model);

    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new EmailManager());

    @Test
    public void executeUndo() throws Exception {
        dummyCommand.execute();
        Files.copy(Paths.get("default.jpeg"), Paths.get("data/images/alice@example.com.jpg"),
                StandardCopyOption.REPLACE_EXISTING);
        deleteFirstPerson(expectedModel);
        assertEquals(expectedModel, model);

        showFirstPersonOnly(model);

        // undo() should cause the model's filtered list to show all persons
        Files.copy(Paths.get("default.jpeg"), Paths.get("data/images/alice@example.com.jpg"),
                StandardCopyOption.REPLACE_EXISTING);
        dummyCommand.undo();
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new EmailManager());
        assertEquals(expectedModel, model);
    }

    @Test
    public void redo() throws Exception {
        showFirstPersonOnly(model);
        Files.copy(Paths.get("default.jpeg"), Paths.get("data/images/alice@example.com.jpg"),
                StandardCopyOption.REPLACE_EXISTING);
        // redo() should cause the model's filtered list to show all persons
        dummyCommand.redo();
        Files.copy(Paths.get("default.jpeg"), Paths.get("data/images/alice@example.com.jpg"),
                StandardCopyOption.REPLACE_EXISTING);
        deleteFirstPerson(expectedModel);
        assertEquals(expectedModel, model);
    }

    /**
     * Deletes the first person in the model's filtered list.
     */
    class DummyCommand extends UndoableCommand {
        DummyCommand(Model model) {
            this.model = model;
        }

        @Override
        public CommandResult executeUndoableCommand() throws CommandException {
            ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(0);
            try {
                model.deletePerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                fail("Impossible: personToDelete was retrieved from model.");
            } catch (IOException ioe) {
                fail("Impossible: Image must exist");
            }
            return new CommandResult("");
        }
    }
}
