package de.datenhahn.vaadin.componentrenderer.demo;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import de.datenhahn.vaadin.componentrenderer.FocusPreserveExtension;
import de.datenhahn.vaadin.componentrenderer.grid.ComponentGrid;
import de.datenhahn.vaadin.componentrenderer.grid.ComponentGridDecorator;
import org.fluttercode.datafactory.impl.DataFactory;

public class ViewComponents {
    private static final int ROW_HEIGHT = 40;


    private static final DataFactory testData = new DataFactory();

    public static CssLayout createRating(Customer customer) {
        CssLayout layout = new CssLayout();
        layout.setHeight(ROW_HEIGHT, Sizeable.Unit.PIXELS);
        layout.setWidth(150, Sizeable.Unit.PIXELS);

        Label overallRating = new Label(FontAwesome.STAR.getHtml(), ContentMode.HTML);
        overallRating.addStyleName("green");
        overallRating.setDescription("Very good : " + testData.getNumberBetween(90, 100) + "% Chance");
        overallRating.setWidthUndefined();
        overallRating.setWidth(40, Sizeable.Unit.PIXELS);
        overallRating.setHeight(ROW_HEIGHT, Sizeable.Unit.PIXELS);
        layout.addComponent(overallRating);


        Label carRating = new Label(FontAwesome.CAR.getHtml(), ContentMode.HTML);
        carRating.addStyleName("red");
        carRating.setDescription("Unlikely : " + testData.getNumberBetween(1, 15) + "%");
        carRating.setWidthUndefined();
        carRating.setWidth(40, Sizeable.Unit.PIXELS);
        carRating.setHeight(ROW_HEIGHT, Sizeable.Unit.PIXELS);

        layout.addComponent(carRating);

        return layout;
    }

    public static Image createDetailsIcons(Grid grid, Customer customer) {
        final Image imageDown = new Image("", new ThemeResource("../demotheme/img/caret-down.png"));
        final Image imageUp = new Image("", new ThemeResource("../demotheme/img/caret-up.png"));
        imageDown.setHeight(32, Sizeable.Unit.PIXELS);
        imageDown.setWidth(32, Sizeable.Unit.PIXELS);
        imageUp.setHeight(32, Sizeable.Unit.PIXELS);
        imageDown.setWidth(32, Sizeable.Unit.PIXELS);

        imageDown.addShortcutListener(new ShortcutListener("enter", ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object sender, Object target) {
                if (sender == imageDown || sender == imageUp) {
                    Notification.show("Shortcut captured");
                    grid.setDetailsVisible(customer, !grid.isDetailsVisible(customer));
                }
            }
        });
        imageDown.addClickListener(event -> grid.setDetailsVisible(customer, true));
        imageUp.addClickListener(event -> grid.setDetailsVisible(customer, false));

        if (grid.isDetailsVisible(customer)) {
            return imageUp;
        } else {
            return imageDown;
        }
    }

    public static Component createFoodIcon(Customer cust) {
        Label label = new Label(FontAwesome.HOURGLASS_2.getHtml(), ContentMode.HTML);

        label.setHeight(32, Sizeable.Unit.PIXELS);
        label.setWidth(50, Sizeable.Unit.PIXELS);

        if (cust.getFood() == Customer.Food.HAMBURGER) {
            label.setValue(FontAwesome.AMBULANCE.getHtml());
        }

        if (cust.getFood() == Customer.Food.FISH) {
            label.setValue(FontAwesome.HEART.getHtml());
        }

        if (cust.getFood() == Customer.Food.VEGETABLES) {
            label.setValue(FontAwesome.SUN_O.getHtml());
        }

        return label;

    }

    public static Button createClassicDeleteButton(Grid grid, FocusPreserveExtension focusPreserveExtension,
                                                   BeanItemContainer<Customer> beanItemContainer, Customer customer) {
        Button delete = new Button("Delete", event -> {
            beanItemContainer.removeItem(customer);
            if (grid instanceof ComponentGrid) {
                ((ComponentGrid) grid).refresh();
            } else {
                // a hack to force rerendering of the grid
                focusPreserveExtension.saveFocus();
                grid.setCellStyleGenerator(grid.getCellStyleGenerator());
                focusPreserveExtension.restoreFocus();
            }

        });
        delete.setHeight(ROW_HEIGHT, Sizeable.Unit.PIXELS);
        delete.setWidth(150, Sizeable.Unit.PIXELS);
        return delete;
    }

    public static Button createDeleteButton(ComponentGridDecorator componentGridDecorator, Customer customer) {
        Button delete = new Button("Delete", event -> {
            componentGridDecorator.getGrid().getContainerDataSource().removeItem(customer);
            componentGridDecorator.refresh();
        });

        delete.setHeight(ROW_HEIGHT, Sizeable.Unit.PIXELS);
        delete.setWidth(150, Sizeable.Unit.PIXELS);
        return delete;
    }

    public static Component createFoodSelector(ComponentGridDecorator componentGridDecorator, Customer
            customer) {

        ComboBox select = new ComboBox();
        select.setWidth(200, Sizeable.Unit.PIXELS);
        select.setHeight(ROW_HEIGHT, Sizeable.Unit.PIXELS);
        select.addItems(Customer.Food.FISH, Customer.Food.HAMBURGER, Customer.Food.VEGETABLES);
        select.setPropertyDataSource(new BeanItem<>(customer).getItemProperty(Customer.FOOD));
        select.addValueChangeListener(e -> {
            componentGridDecorator.refresh();
        });
        return select;
    }


    public static Component createClassicFoodSelector(Grid grid, FocusPreserveExtension focusPreserveExtension, Customer
            customer) {

        ComboBox select = new ComboBox();
        select.setWidth(200, Sizeable.Unit.PIXELS);
        select.setHeight(ROW_HEIGHT, Sizeable.Unit.PIXELS);
        select.addItems(Customer.Food.FISH, Customer.Food.HAMBURGER, Customer.Food.VEGETABLES);
        select.setPropertyDataSource(new BeanItem<>(customer).getItemProperty(Customer.FOOD));
        select.addValueChangeListener(e -> {
            if (grid instanceof ComponentGrid) {
                ((ComponentGrid) grid).refresh();
            } else {
                // a hack to force rerendering of the grid
                focusPreserveExtension.saveFocus();
                grid.setCellStyleGenerator(grid.getCellStyleGenerator());
                focusPreserveExtension.restoreFocus();
            }
        });
        return select;
    }

    public static CheckBox createEnableDisableCheckBox(final Grid myGrid) {
        CheckBox checkBox = new CheckBox("enable/disable");
        checkBox.setValue(myGrid.isEnabled());
        checkBox.addValueChangeListener(event -> myGrid.setEnabled(!myGrid.isEnabled()));
        return checkBox;
    }


}