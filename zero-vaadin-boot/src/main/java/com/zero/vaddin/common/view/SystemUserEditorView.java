package com.zero.vaddin.common.view;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.zero.vaddin.common.repository.SystemUserRepository;
import com.zero.vaddin.domain.entity.SystemUserEntity;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A simple example to introduce building forms. As your real application is probably much
 * more complicated than this example, you could re-use this form in multiple places. This
 * example component is only used in MainView.
 * <p>
 * In a real world application you'll most likely using a common super class for all your
 * forms - less code, better UX.
 */
@UIScope
@SpringComponent
public class SystemUserEditorView extends VerticalLayout implements KeyNotifier {

	private final SystemUserRepository repository;

	/**
	 * The currently edited customer
	 */
	private SystemUserEntity systemUserEntity;

	/* Fields to edit properties in Customer entity */
	TextField firstName = new TextField("First name");
	TextField lastName = new TextField("Last name");

	/* Action buttons */
	Button save = new Button("Save", VaadinIcon.CHECK.create());
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", VaadinIcon.TRASH.create());
	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

	Binder<SystemUserEntity> binder = new Binder<>(SystemUserEntity.class);
    // ChangeHandler is notified when either save or delete
    // is clicked
    @Setter
    private ChangeHandler changeHandler;

	@Autowired
	public SystemUserEditorView(SystemUserRepository repository) {
		this.repository = repository;

		add(firstName, lastName, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);

		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

		addKeyPressListener(Key.ENTER, e -> save());

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		cancel.addClickListener(e -> editCustomer(systemUserEntity));
		setVisible(false);
	}

	void delete() {
		repository.delete(systemUserEntity);
		changeHandler.onChange();
	}

	void save() {
		repository.save(systemUserEntity);
		changeHandler.onChange();
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editCustomer(SystemUserEntity c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			// In a more complex app, you might want to load
			// the entity/DTO with lazy loaded relations for editing
			systemUserEntity = repository.findById(c.getId()).get();
		}
		else {
			systemUserEntity = c;
		}
		cancel.setVisible(persisted);

		// Bind customer properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(systemUserEntity);

		setVisible(true);

		// Focus first name initially
		firstName.focus();
	}

}