package code;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import icons.Icons;
import org.jetbrains.annotations.NotNull;
import utils.Notifications;

import java.util.Collection;

public class LineMarkerProvider extends RelatedItemLineMarkerProvider implements Icons {
    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) throws ArrayIndexOutOfBoundsException{
        if (element instanceof PsiAnnotation) {
            PsiAnnotation annotation = (PsiAnnotation) element;
            String value = annotation.getQualifiedName() instanceof String ? annotation.getQualifiedName() : null;
            if(value != null) {
                if (value.startsWith("ButtonEventHandler") || value.startsWith("FieldEventHandler")) {
                    NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(ESDK_BOAT_GUTTER)
                            .setTarget(annotation.getParameterList().getAttributes()[0].getValue().getChildren()[0])
                            .setTooltipText("Navigate to field");
                    result.add(builder.createLineMarkerInfo(element));
                } else if (value.startsWith("ScreenEventHandler")) {
                    NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(ESDK_BOAT_GUTTER)
                            .setTarget(annotation.getParameterList().getAttributes()[0].getValue().getChildren()[0])
                            .setTooltipText("Navigate to field");
                    result.add(builder.createLineMarkerInfo(element));
                } else if (value.startsWith("RunFopWith")){
                    NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(ESDK_BOAT_GUTTER)
                            .setTarget(annotation.getParameterList().getAttributes()[0].getValue().getChildren()[0])
                            .setTooltipText("Navigate to Class");
                    result.add(builder.createLineMarkerInfo(element));
                }else if (value.startsWith("EventHandler")){
                    NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(ESDK_BOAT_GUTTER)
                            .setTarget(annotation.getParameterList().getAttributes()[0].getValue().getChildren()[0])
                            .setTooltipText("Navigate to Class");
                    result.add(builder.createLineMarkerInfo(element));
                }else if ( value.startsWith("RunFop")){
                    NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(ESDK_BOAT_GUTTER)
                            .setTarget(element)
                            .setTooltipText("Navigate to Class");
                    result.add(builder.createLineMarkerInfo(element));
                }
            }
        }
    }
}

