package code;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import icons.Icons;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * IntelliJ Line Marker Provider
 */
public class LineMarkerProvider extends RelatedItemLineMarkerProvider implements Icons {
    @Override
    protected void collectNavigationMarkers(@NotNull final PsiElement element, @NotNull final Collection<? super RelatedItemLineMarkerInfo> result) throws ArrayIndexOutOfBoundsException{
        if (element instanceof PsiAnnotation) {
            final PsiAnnotation annotation = (PsiAnnotation) element;
            final String value = annotation.getQualifiedName() instanceof String ? annotation.getQualifiedName() : null;
            if(value != null) {
                if (value.startsWith("ButtonEventHandler") || value.startsWith("FieldEventHandler")) {
                    final NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(ESDK_BOAT_GUTTER)
                            .setTarget(annotation.getParameterList().getAttributes()[0].getValue().getChildren()[0])
                            .setTooltipText("Navigate to field");
                    result.add(builder.createLineMarkerInfo(element));
                } else if (value.startsWith("ScreenEventHandler")) {
                    final NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(ESDK_BOAT_GUTTER)
                            .setTarget(annotation.getParameterList().getAttributes()[0].getValue().getChildren()[0])
                            .setTooltipText("Navigate to field");
                    result.add(builder.createLineMarkerInfo(element));
                } else if (value.startsWith("RunFopWith")){
                    final NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(ESDK_BOAT_GUTTER)
                            .setTarget(annotation.getParameterList().getAttributes()[0].getValue().getChildren()[0])
                            .setTooltipText("Navigate to Class");
                    result.add(builder.createLineMarkerInfo(element));
                }else if (value.startsWith("EventHandler")){
                    final NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(ESDK_BOAT_GUTTER)
                            .setTarget(annotation.getParameterList().getAttributes()[0].getValue().getChildren()[0])
                            .setTooltipText("Navigate to Class");
                    result.add(builder.createLineMarkerInfo(element));
                }else if ( value.startsWith("RunFop")){
                    final NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(ESDK_BOAT_GUTTER)
                            .setTarget(element)
                            .setTooltipText("Navigate to Class");
                    result.add(builder.createLineMarkerInfo(element));
                }
            }
        }
    }
}

