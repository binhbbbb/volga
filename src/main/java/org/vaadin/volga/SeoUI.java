package org.vaadin.volga;

import com.github.wolfie.history.HistoryExtension;
import com.github.wolfie.history.PushStateLink;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("valo")
@Title("SEO test: basic title")
public class SeoUI extends UI implements ViewDisplay {

    private MVerticalLayout layout = new MVerticalLayout();

    private Navigator navigator;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setupNavigation();
        layout();
    }

    private void setupNavigation() {
        navigator = HistoryExtension.createNavigationStateManager(this, this);
    }

    private void layout() {
        MVerticalLayout main = new MVerticalLayout(new Menu(), layout);
        setContent(main);
    }

    @Override
    public void showView(View view) {
        layout.removeAllComponents();
        layout.addComponent((Component) view);
    }

    class Menu extends MHorizontalLayout {

        public Menu() {
            addView("Main view", "");
            addView("Second view", "second");
        }

        private void addView(String name, String path) {
            PushStateLink link = new PushStateLink(name, path);
            add(link);
            Volga volga = Volga.getCurrent(VaadinServlet.getCurrent().getServletContext());
            navigator.addView(path, volga.getViewByPath(path).get());
        }
    }
}