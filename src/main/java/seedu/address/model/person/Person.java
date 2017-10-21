package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person implements ReadOnlyPerson {

    private ObjectProperty<Name> name;
    private ObjectProperty<Phone> phone;
    private ObjectProperty<Email> email;
    private ObjectProperty<Address> address;
    private ObjectProperty<Photo> photo;

    private ObjectProperty<UniqueTagList> tags;
    private ObjectProperty<Birthdate> birthdate;

    /**
     * Every field must be present and not null.
     */

    public Person(Name name, Phone phone, Email email, Address address, Photo photo, Set<Tag> tags,
                  Birthdate birthdate) {
        requireAllNonNull(name, phone, email, address, tags, birthdate);

        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.photo = new SimpleObjectProperty<>(photo);
        this.birthdate = new SimpleObjectProperty<>(birthdate);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));

    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(),
                source.getPhoto(), source.getTags(), source.getBirthdate());
    }

    public void setName(Name name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<Name> nameProperty() {
        return name;
    }

    @Override
    public Name getName() {
        return name.get();
    }

    public void setPhone(Phone phone) {
        this.phone.set(requireNonNull(phone));
    }

    @Override
    public ObjectProperty<Phone> phoneProperty() {
        return phone;
    }

    @Override
    public Phone getPhone() {
        return phone.get();
    }

    public void setEmail(Email email) {
        this.email.set(requireNonNull(email));
    }

    @Override
    public ObjectProperty<Email> emailProperty() {
        return email;
    }

    @Override
    public Email getEmail() {
        return email.get();
    }

    public void setAddress(Address address) {
        this.address.set(requireNonNull(address));
    }

    @Override
    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    @Override
    public Address getAddress() {
        return address.get();
    }

    @Override
    public ObjectProperty<Birthdate> birthdateProperty() {
        return birthdate;
    }

    @Override
    public Birthdate getBirthdate() {
        return birthdate.get();
    }

    public void setBirthdate(Birthdate birthdate) {
        this.birthdate.set(requireNonNull(birthdate));
    }

    public void setPhoto(Photo photo) {
        this.photo.set(photo);
    }

    @Override
    public ObjectProperty<Photo> photoProperty() {
        return photo;
    }

    @Override
    public Photo getPhoto() {
        return photo.get();
    }


    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get().toSet());
    }

    public ObjectProperty<UniqueTagList> tagProperty() {
        return tags;
    }

    /**
     * Replaces this person's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

    /**
     * Check if this person have the given tags in argument tag set
     */
    @Override
    public boolean containsTags(List<String> tags) {
        for (Tag t : this.tags.get().toSet()) {
            boolean found = tags.stream().anyMatch(
                    tag -> StringUtil.containsWordIgnoreCase(t.tagName,tag));
            if (found) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, photo, tags, birthdate);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
