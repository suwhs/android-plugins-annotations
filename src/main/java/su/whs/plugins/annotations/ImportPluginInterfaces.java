package su.whs.plugins.annotations;

/**
 * Created by igor n. boulliev on 12.08.15.
 */

/**
 * declare list of interfaces, used in app
 * required to generate source code for module, if module does not contains own implementation
 */
public @interface ImportPluginInterfaces {
    String[] list() default {};
}
