package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.exceptions.InvalidTaskIndexException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.TaskAlreadyCompleteException;

/**
 * Marks a specific undone task as done for a particular student.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a specific undone task as done for a particular student. "
            + "Parameters: STUDENT_ID and INDEX \n"
            + "Example: " + COMMAND_WORD + PREFIX_ID + "A0123456Z" + PREFIX_INDEX + "1";

    public static final String MARKED_TASK_SUCCESS = "Task for %1$s marked";

    private final StudentId studentId;
    private final Index index;

    /**
     * Creates a MarkCommand to mark the {@code Task} of the specified {@code Person} using the specified index.
     */
    public MarkCommand(StudentId studentId, Index index) {
        requireNonNull(studentId);
        requireNonNull(index);

        this.studentId = studentId;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        try {
            requireNonNull(model);
            model.markTaskOfPerson(studentId, index);
        } catch (InvalidTaskIndexException invalidTaskIndexException) {
            throw new InvalidTaskIndexException();
        } catch (TaskAlreadyCompleteException taskAlreadyCompleteException) {
            throw new TaskAlreadyCompleteException();
        } catch (PersonNotFoundException personNotFoundException) {
            throw new PersonNotFoundException();
        } finally {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }

        return new CommandResult(String.format(MARKED_TASK_SUCCESS, studentId));
    }
}