/* 
 * ArimAPI-env-core
 * Copyright © 2020 Anand Beh <https://www.arim.space>
 * 
 * ArimAPI-env-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ArimAPI-env-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ArimAPI-env-core. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU General Public License.
 */
package space.arim.api.env;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiPredicate;

import org.spongepowered.api.text.LiteralText;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextFormat;
import org.spongepowered.api.text.format.TextStyle;
import org.spongepowered.api.text.format.TextStyles;

import space.arim.api.chat.ClickAction;
import space.arim.api.chat.Colour;
import space.arim.api.chat.Colour.ColourCatalog;
import space.arim.api.chat.Component;
import space.arim.api.chat.Format;
import space.arim.api.chat.HoverAction;
import space.arim.api.chat.JsonComponent;
import space.arim.api.chat.JsonMessageBuilder;
import space.arim.api.chat.Message;
import space.arim.api.chat.ShiftClickAction;
import space.arim.api.chat.Style;
import space.arim.api.chat.Style.StyleCatalog;

/**
 * Enables compatibility with the Sponge API. <br>
 * Contains mirror methods for converting between respective API classes. <br>
 * <br>
 * <b>Sponge Text API</b>: Comparison with ArimAPI <br>
 * <br>
 * <i>Colours and Styles</i> <br>
 * ArimAPI uses <code>null</code> values to designate a lack of colouring in a message. The Sponge API avoids <code>null</code> at all costs.
 * Accordingly, its <code>TextColors.NONE</code> and <code>TextStyles.NONE</code> translate to <code>null</code> in ArimAPI.
 * Also, Sponge's <code>TextStyle</code> represents a composite of multiple styles, whereas ArimAPI simply uses an array of <code>Style</code>s. <br>
 * <br>
 * <i>Json Messages</i> <br>
 * ArimAPI enables tooltips, urls, commands, suggestions, and insertions. The Sponge API has equivalent support for each of these. 
 * The Sponge API includes additional hover event and click event actions.
 * 
 * @author A248
 *
 */
/*
 * WIP
 */
/*public*/ class SpongeTextConverter implements PlatformMessageConverter<Text> {
	
	/**
	 * <b>ArimAPI {@literal -}{@literal >} Sponge API</b>: Formatting codes <br>
	 * Converts from a {@link Colour} to a <code>TextColor</code>.
	 * 
	 * @param colour the source colour
	 * @return the equivalent <code>TextColor</code>
	 */
	public TextColor convert(Colour colour) {
		if (colour == null) {
			return TextColors.NONE;
		}
		switch (ColourCatalog.valueOf(colour)) {
		case BLACK:
			return TextColors.BLACK;
		case DARK_BLUE:
			return TextColors.DARK_BLUE;
		case DARK_GREEN:
			return TextColors.DARK_GREEN;
		case DARK_AQUA:
			return TextColors.DARK_AQUA;
		case DARK_RED:
			return TextColors.DARK_RED;
		case DARK_PURPLE:
			return TextColors.DARK_PURPLE;
		case GOLD:
			return TextColors.GOLD;
		case GRAY:
			return TextColors.GRAY;
		case DARK_GRAY:
			return TextColors.DARK_GRAY;
		case BLUE:
			return TextColors.BLUE;
		case GREEN:
			return TextColors.GREEN;
		case AQUA:
			return TextColors.AQUA;
		case RED:
			return TextColors.RED;
		case LIGHT_PURPLE:
			return TextColors.LIGHT_PURPLE;
		case YELLOW:
			return TextColors.YELLOW;
		case WHITE:
			return TextColors.WHITE;
		default:
			throw new IllegalStateException("No corresponding TextColor for &" + colour.identifier());
		}
	}
	
	/**
	 * <b>Sponge API {@literal -}{@literal >} ArimAPI</b>: Formatting codes <br>
	 * Converts from a <code>TextColor</code> to a {@link Colour}. <br>
	 * <br>
	 * Note that there is no equivalent in ArimAPI for <code>TextColors.NONE</code> and <code>TextColors.REST</code>,
	 * so <code>null</code> will be returned if either of these <code>TextColor</code>s are passed as parameters.
	 * 
	 * @param colour the source colour
	 * @return the equivalent {@link Colour}, or <code>null</code> if there is none
	 */
	public Colour convert(TextColor colour) {
		if (colour != null) {
			for (ColourCatalog entry : ColourCatalog.values()) {
				if (convert(entry.getColourValue()).getColor().equals(colour.getColor())) {
					return entry.getColourValue();
				}
			}
		}
		return null;
	}
	
	private <T> TextStyle convertStyles(T styles, BiPredicate<T, Style> checker) {
		return (styles == null) ? null : new TextStyle(checker.test(styles, Style.BOLD), checker.test(styles, Style.ITALIC), checker.test(styles, Style.UNDERLINE), checker.test(styles, Style.STRIKETHROUGH), checker.test(styles, Style.MAGIC));
	}
	
	/**
	 * <b>ArimAPI {@literal -}{@literal >} Sponge API</b>: Formatting codes <br>
	 * Converts from a {@link Style} to a <code>TextStyle</code>.
	 * 
	 * @param styles the source style set
	 * @return an equivalent <code>TextStyle</code>
	 */
	public TextStyle convert(Set<Style> styles) {
		return convertStyles(styles, (styleSet, style) -> styleSet.contains(style));
	}
	
	/**
	 * <b>ArimAPI {@literal -}{@literal >} Sponge API</b>: Formatting codes <br>
	 * Converts from a {@link Style} to a <code>TextStyle</code>.
	 * 
	 * @param styles the source style array
	 * @return an equivalent <code>TextStyle</code>
	 */
	public TextStyle convert(Style...styles) {
		return convertStyles(styles, (styleArray, style) -> {
			for (Style inArray : styleArray) {
				if (style == inArray) {
					return true;
				}
			}
			return false;
		});
	}
	
	/**
	 * <b>Sponge API {@literal -}{@literal >} ArimAPI</b>: Formatting codes <br>
	 * Converts from a <code>TextStyle</code> to a {@link Style}.
	 * 
	 * @param styling the source <code>TextStyle</code>
	 * @return an equivalent set of <code>Style</code>s
	 */
	public Set<Style> convert(TextStyle styling) {
		if (styling == null) {
			return null;
		}
		Set<Style> styles = new HashSet<Style>();
		if (styling.contains(TextStyles.OBFUSCATED)) {
			styles.add(Style.MAGIC);
		}
		if (styling.contains(TextStyles.BOLD)) {
			styles.add(Style.BOLD);
		}
		if (styling.contains(TextStyles.STRIKETHROUGH)) {
			styles.add(Style.STRIKETHROUGH);
		}
		if (styling.contains(TextStyles.UNDERLINE)) {
			styles.add(Style.UNDERLINE);
		}
		if (styling.contains(TextStyles.ITALIC)) {
			styles.add(Style.ITALIC);
		}
		return styles;
	}
	
	/**
	 * <b>ArimAPI {@literal -}{@literal >} Sponge API</b>: Formatting codes <br>
	 * Converts from a {@link Format} to a <code>TextFormat</code>.
	 * 
	 * @param formats the source format array
	 * @return an equivalent <code>TextFormat</code>
	 */
	public TextFormat convert(Format...formats) {
		if (formats == null) {
			return null;
		} else if (formats.length == 0) {
			Format format = formats[0];
			return (format instanceof Style) ? TextFormat.of(convert(new Style[] {(Style) format})) : (format instanceof Colour) ? TextFormat.of(convert((Colour) format)) : TextFormat.NONE;
		}
		TextColor colour = TextColors.NONE;
		TextStyle style = TextStyles.NONE;
		for (Format format : formats) {
			if (format instanceof Style) {
				style = style.and(convert(new Style[] {(Style) format}));
			} else if (format instanceof Colour) {
				colour = convert((Colour) format);
			}
		}
		return TextFormat.of(colour, style);
	}
	
	/**
	 * <b>Sponge API {@literal -}{@literal >} ArimAPI</b>: Formatting codes <br>
	 * Converts from a <code>TextFormat</code> to a {@link Format}.
	 * 
	 * @param format the source <code>TextFormat</code>
	 * @return an equivalent set of <code>Format</code>s
	 */
	public Set<Format> convert(TextFormat format) {
		if (format == null) {
			return null;
		}
		Set<Format> formatting = new HashSet<Format>();
		formatting.addAll(convert(format.getStyle()));
		Colour colour = convert(format.getColor());
		if (colour != null) {
			formatting.add(colour);
		}
		return formatting;
	}
	
	/**
	 * <b>ArimAPI {@literal -}{@literal >} Sponge API</b>: Click Actions <br>
	 * Converts from a {@link ClickAction} to a <code>ClickAction</code>
	 * 
	 * @param clickAction the click action
	 * @return an equivalent <code>ClickEvent</code>, null if the input is null or not supported
	 */
	// Private because we do not officially support conversion of individual actions
	private org.spongepowered.api.text.action.ClickAction<?> convert(ClickAction clickAction) {
		if (clickAction == null) {
			return null;
		}
		ClickAction.Type clickActionType = clickAction.getType();
		switch (clickActionType) {
		case RUN_COMMAND:
			return TextActions.runCommand(clickAction.getValue());
		case SUGGEST_COMMAND:
			return TextActions.suggestCommand(clickAction.getValue());
		case OPEN_URL:
			try {
				return TextActions.openUrl(new URL(clickAction.getValue()));
			} catch (MalformedURLException ex) {
				// We can't do anything about this
				// blame Sponge for lack of flexibility
				return null;
			}
		default:
			throw new IllegalStateException("Not implemented for " + clickActionType);
		}
	}
	
	/**
	 * <b>Sponge API {@literal -}{@literal >} ArimAPI</b>: Click Actions <br>
	 * Converts from a <code>ClickAction</code> to a {@link ClickAction}
	 * 
	 * @param clickAction the click action
	 * @return an equivalent {@link ClickAction}, null if the input is null or not supported
	 */
	// Private because we do not officially support conversion of individual actions
	private ClickAction convert(org.spongepowered.api.text.action.ClickAction<?> clickAction) {
		if (clickAction instanceof org.spongepowered.api.text.action.ClickAction.RunCommand) {
			return ClickAction.runCommand((String) clickAction.getResult());
		} else if (clickAction instanceof org.spongepowered.api.text.action.ClickAction.SuggestCommand) {
			return ClickAction.suggestCommand((String) clickAction.getResult());
		} else if (clickAction instanceof org.spongepowered.api.text.action.ClickAction.OpenUrl) {
			return ClickAction.openUrl(((URL) clickAction.getResult()).toString());
		}
		// There's nothing we can do about this; it isn't available in our API
		return null;
	}
	
	/**
	 * <b>ArimAPI {@literal -}{@literal >} Sponge API</b>: Messages <br>
	 * Converts from a {@link Component} to a <code>Text</code>.
	 * 
	 * @param component the source component
	 * @return an equivalent <code>Text</code>
	 */
	public Text convert(Component component) {
		if (component == null) {
			return null;
		}
		Text.Builder builder = Text.builder(component.getText()).color(convert(component.getColour()));
		Set<TextStyle> styles = new HashSet<>();
		for (StyleCatalog styleCatalog : StyleCatalog.values()) {
			Style style = styleCatalog.getStyleValue();
			if (component.hasStyle(style)) {
				styles.add(convert(style));
			}
		}
		builder.style(styles.toArray(new TextStyle[] {}));
		if (component instanceof JsonComponent) {
			JsonComponent json = (JsonComponent) component;
			HoverAction hoverAction = json.getHoverAction();
			if (hoverAction != null) {
				builder.onHover(TextActions.showText(convertFrom(hoverAction.getMessage())));
			}
			builder.onClick(convert(json.getClickAction()));
			ShiftClickAction shiftClickAction = json.getShiftClickAction();
			if (shiftClickAction != null) {
				builder.onShiftClick(TextActions.insertText(shiftClickAction.getInsertion()));
			}
		}
		return builder.build();
	}
	
	/**
	 * <b>ArimAPI {@literal -}{@literal >} Sponge API</b>: Messages <br>
	 * Converts from a {@link Message} to a <code>Text</code>.
	 * 
	 * @param message the source message
	 * @return an equivalent <code>Text</code>
	 */
	@Override
	public Text convertFrom(Message message) {
		if (message == null) {
			return null;
		}
		Text.Builder builder = Text.builder();
		for (Component component : message.getComponents()) {
			builder.append(convert(component), Text.builder().color(TextColors.RESET).style(TextStyles.RESET).build());
		}
		return builder.build();
	}
	
	private void addAllContent(JsonMessageBuilder builder, Text message) {
		builder.reset().colour(convert(message.getColor()))
				.style(Style.MAGIC, message.getStyle().isObfuscated().orElse(false))
				.style(Style.BOLD, message.getStyle().isBold().orElse(false))
				.style(Style.STRIKETHROUGH, message.getStyle().hasStrikethrough().orElse(false))
				.style(Style.UNDERLINE, message.getStyle().hasUnderline().orElse(false))
				.style(Style.ITALIC, message.getStyle().isItalic().orElse(false));
		if (message instanceof LiteralText) {
			builder.add(((LiteralText) message).getContent());
		}
		message.getHoverAction().ifPresent((hover) -> {
			if (hover instanceof org.spongepowered.api.text.action.HoverAction.ShowText) {
				builder.hoverAction(HoverAction.showTooltip(convertTo((Text) hover.getResult())));
			}
		});
		message.getClickAction().ifPresent((click) -> {
			builder.clickAction(convert(click));
		});
		message.getShiftClickAction().ifPresent((shiftclick) -> {
			if (shiftclick instanceof org.spongepowered.api.text.action.ShiftClickAction.InsertText) {
				builder.insertText((String) shiftclick.getResult());
			}
		});
		for (Text child : message.getChildren()) {
			addAllContent(builder, child);
		}
	}
	
	/**
	 * <b>Sponge API {@literal -}{@literal >} ArimAPI</b>: Messages <br>
	 * Converts from a <code>Text</code> to a {@link Message}.
	 * 
	 * @param message the source message
	 * @return an equivalent <code>Message</code>
	 */
	@Override
	public Message convertTo(Text message) {
		if (message == null) {
			return null;
		}
		JsonMessageBuilder builder = new JsonMessageBuilder();
		addAllContent(builder, message);
		return builder.cleanBuild();
	}
	
}