package vn.com.onesoft.bigfox.io.core.session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.com.onesoft.bigfox.io.message.annotations.Property;

public class BigFox {

	public static final byte NULL = 0;
	public static final byte NOT_NULL = 1;
	public static final byte INT = 1;
	public static final byte SHORT = 2;
	public static final byte BYTE = 3;
	public static final byte LONG = 4;
	public static final byte FLOAT = 5;
	public static final byte DOUBLE = 6;
	public static final byte BOOLEAN = 7;
	public static final byte CHAR = 8;
	public static final byte STRING = 9;
	public static final byte OBJECT = 10;
	public static final byte ARRAY_INT = 11;
	public static final byte ARRAY_SHORT = 12;
	public static final byte ARRAY_BYTE = 13;
	public static final byte ARRAY_LONG = 14;
	public static final byte ARRAY_FLOAT = 15;
	public static final byte ARRAY_DOUBLE = 16;
	public static final byte ARRAY_BOOLEAN = 17;
	public static final byte ARRAY_CHAR = 18;
	public static final byte ARRAY_STRING = 19;
	public static final byte ARRAY_OBJECT = 20;

	public static byte[] toBytes(Object object) throws IOException {
		if (object == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		write(object, out);
		byte[] data = baos.toByteArray();
		return data;
	}

	private static void read(Object object, DataInputStream dis)
			throws Exception {
		int nFields = dis.readByte();
		List<Field> fields = object == null ? null
				: getInheritedPrivateFields(object.getClass());
		for (int j = 0; j < nFields; j++) {
			String name = dis.readUTF();
			byte type = dis.readByte();
			Field f = null;
			if (object != null) {
				for (int i = 0, n = fields.size(); i < n; i++) {
					Field field = fields.get(i);
					field.setAccessible(true);
					Property seq = field.getAnnotation(Property.class);
					String sname = "".equals(seq.name()) ? field.getName()
							: seq.name();

					if (name.equals(sname)) {
						f = field;
						break;
					}
				}
			}
			if (type == NULL) {
			} else if (type == INT) {
				int v = dis.readInt();
				if (f != null) {
					f.setInt(object, v);
				}
			} else if (type == SHORT) {
				short v = dis.readShort();
				if (f != null) {
					f.setShort(object, v);
				}
			} else if (type == BYTE) {
				byte v = dis.readByte();
				if (f != null) {
					f.setByte(object, v);
				}
			} else if (type == LONG) {
				long v = dis.readLong();
				if (f != null) {
					f.setLong(object, v);
				}
			} else if (type == FLOAT) {
				float v = dis.readFloat();
				if (f != null) {
					f.setFloat(object, v);
				}
			} else if (type == DOUBLE) {
				double v = dis.readDouble();
				if (f != null) {
					f.setDouble(object, v);
				}
			} else if (type == BOOLEAN) {
				boolean v = dis.readBoolean();
				if (f != null) {
					f.setBoolean(object, v);
				}
			} else if (type == CHAR) {
				char v = dis.readChar();
				if (f != null) {
					f.setChar(object, v);
				}
			} else if (type == STRING) {
				String v = dis.readUTF();
				if (f != null) {
					f.set(object, v);
				}
			} else if (type == ARRAY_INT) {
				int length = dis.readInt();
				int[] val = new int[length];
				for (int k = 0; k < length; k++) {
					val[k] = dis.readInt();
				}
				if (f != null) {
					f.set(object, val);
				}
			} else if (type == ARRAY_SHORT) {
				int length = dis.readInt();
				short[] val = new short[length];
				for (int k = 0; k < length; k++) {
					val[k] = dis.readShort();
				}
				if (f != null) {
					f.set(object, val);
				}
			} else if (type == ARRAY_BYTE) {
				int length = dis.readInt();
				byte[] val = new byte[length];
				for (int k = 0; k < length; k++) {
					val[k] = dis.readByte();
				}
				if (f != null) {
					f.set(object, val);
				}
			} else if (type == ARRAY_LONG) {
				int length = dis.readInt();
				long[] val = new long[length];
				for (int k = 0; k < length; k++) {
					val[k] = dis.readLong();
				}
				if (f != null) {
					f.set(object, val);
				}
			} else if (type == ARRAY_FLOAT) {
				int length = dis.readInt();
				float[] val = new float[length];
				for (int k = 0; k < length; k++) {
					val[k] = dis.readFloat();
				}
				if (f != null) {
					f.set(object, val);
				}
			} else if (type == ARRAY_DOUBLE) {
				int length = dis.readInt();
				double[] val = new double[length];
				for (int k = 0; k < length; k++) {
					val[k] = dis.readDouble();
				}
				if (f != null) {
					f.set(object, val);
				}
			} else if (type == ARRAY_BOOLEAN) {
				int length = dis.readInt();
				boolean[] val = new boolean[length];
				for (int k = 0; k < length; k++) {
					val[k] = dis.readBoolean();
				}
				if (f != null) {
					f.set(object, val);
				}
			} else if (type == ARRAY_CHAR) {
				int length = dis.readInt();
				char[] val = new char[length];
				for (int k = 0; k < length; k++) {
					val[k] = dis.readChar();
				}
				if (f != null) {
					f.set(object, val);
				}
			} else if (type == ARRAY_STRING) {
				int length = dis.readInt();
				String[] val = new String[length];
				for (int k = 0; k < length; k++) {
					byte bNull = dis.readByte();
					if (bNull == NOT_NULL) {
						val[k] = dis.readUTF();
					}
				}
				if (f != null) {
					if (List.class.isAssignableFrom(f.getType())) {
						List v = new ArrayList();
						v.addAll(Arrays.asList(val));
						f.set(object, v);
					} else {
						f.set(object, val);
					}
				}
			} else if (type == ARRAY_OBJECT) {
				int length = dis.readInt();
				if (f != null) {
					Class<?> element = null;
					if (f.getType().isArray()) {
						element = f.getType().getComponentType();
					} else {
						ParameterizedType integerListType = (ParameterizedType) f
								.getGenericType();
						element = (Class<?>) integerListType
								.getActualTypeArguments()[0];
					}
					Object val = Array.newInstance(element, length);
					for (int k = 0; k < length; k++) {
						byte bNull = dis.readByte();
						if (bNull == NOT_NULL) {
							Object e = element.newInstance();
							read(e, dis);
							Array.set(val, k, e);
						}
					}
					if (List.class.isAssignableFrom(f.getType())) {
						List v = new ArrayList();
						for (int k = 0; k < length; k++) {
							Object o = Array.get(val, k);
							v.add(o);
						}
						f.set(object, v);
					} else {
						f.set(object, val);
					}
				} else {
					for (int k = 0; k < length; k++) {
						byte bNull = dis.readByte();
						if (bNull == NOT_NULL) {
							read(null, dis);
						}
					}
				}
			} else {
				if (f != null) {
					Class<?> clazz = f.getType();
					Object val = clazz.newInstance();
					read(val, dis);
					f.set(object, val);
				} else {
					read(null, dis);
				}
			}
		}

	}

	public static <T> T fromBytes(Class<T> clazz, byte[] bytes)
			throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		DataInputStream dis = new DataInputStream(bais);
		return fromBytes(clazz, dis);
	}

	public static <T> T fromBytes(Class<T> clazz, DataInputStream in)
			throws Exception {
		try {
			T object = clazz.newInstance();
			read(object, in);
			return object;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String toString(Object object) {
		try {
			return toString(toBytes(object));
		} catch (Exception e) {
			e.printStackTrace();
			return "toString Error";
		}
	}

	public static String toString(byte[] data) {
		DataInputStream dis = new DataInputStream(
				new ByteArrayInputStream(data));
		return toString(dis);
	}

	public static String toString(DataInputStream in) {
		try {
			StringBuilder sb = new StringBuilder();
			toString(in, sb, 0);
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "toString Error";
		}
	}

	private static final int TAB = 2;

	private static void toString(DataInputStream dis, StringBuilder sb,
			int indent) throws IOException {
		int nFields = dis.readByte();
		sb.append("{\n");
		for (int j = 0; j < nFields; j++) {
			String name = dis.readUTF();
			indent(sb, indent + TAB);
			sb.append(String.format("%s: ", name));
			byte type = dis.readByte();
			if (type == NULL) {
				sb.append("null");
			} else if (type == INT) {
				int v = dis.readInt();
				sb.append(v);
			} else if (type == SHORT) {
				short v = dis.readShort();
				sb.append(v);
			} else if (type == BYTE) {
				byte v = dis.readByte();
				sb.append(v);
			} else if (type == LONG) {
				long v = dis.readLong();
				sb.append(v);
			} else if (type == FLOAT) {
				float v = dis.readFloat();
				sb.append(v);
			} else if (type == DOUBLE) {
				double v = dis.readDouble();
				sb.append(v);
			} else if (type == BOOLEAN) {
				boolean v = dis.readBoolean();
				sb.append(v);
			} else if (type == CHAR) {
				char v = dis.readChar();
				sb.append(v);
			} else if (type == STRING) {
				String v = dis.readUTF();
				sb.append(String.format("\"%s\"", v));
			} else if (type == ARRAY_INT) {
				int length = dis.readInt();
				sb.append('[');
				for (int k = 0; k < length; k++) {
					int v = dis.readInt();
					sb.append(v);
					if (k != length - 1) {
						sb.append(',');
					}
				}
				sb.append(']');
			} else if (type == ARRAY_SHORT) {
				int length = dis.readInt();
				sb.append('[');
				for (int k = 0; k < length; k++) {
					short v = dis.readShort();
					sb.append(v);
					if (k != length - 1) {
						sb.append(',');
					}
				}
				sb.append(']');
			} else if (type == ARRAY_BYTE) {
				int length = dis.readInt();
				sb.append('[');
				for (int k = 0; k < length; k++) {
					byte v = dis.readByte();
					sb.append(v);
					if (k != length - 1) {
						sb.append(',');
					}
				}
				sb.append(']');
			} else if (type == ARRAY_LONG) {
				int length = dis.readInt();
				sb.append('[');
				for (int k = 0; k < length; k++) {
					long v = dis.readLong();
					sb.append(v);
					if (k != length - 1) {
						sb.append(',');
					}
				}
				sb.append(']');
			} else if (type == ARRAY_FLOAT) {
				int length = dis.readInt();
				sb.append('[');
				for (int k = 0; k < length; k++) {
					float v = dis.readFloat();
					sb.append(v);
					if (k != length - 1) {
						sb.append(',');
					}
				}
			} else if (type == ARRAY_DOUBLE) {
				int length = dis.readInt();
				sb.append('[');
				for (int k = 0; k < length; k++) {
					double v = dis.readDouble();
					sb.append(v);
					if (k != length - 1) {
						sb.append(',');
					}
				}
				sb.append(']');
			} else if (type == ARRAY_BOOLEAN) {
				int length = dis.readInt();
				sb.append('[');
				for (int k = 0; k < length; k++) {
					boolean v = dis.readBoolean();
					sb.append(v);
					if (k != length - 1) {
						sb.append(',');
					}
				}
				sb.append(']');
			} else if (type == ARRAY_CHAR) {
				int length = dis.readInt();
				sb.append('[');
				for (int k = 0; k < length; k++) {
					char v = dis.readChar();
					sb.append(v);
					if (k != length - 1) {
						sb.append(',');
					}
				}
				sb.append(']');
			} else if (type == ARRAY_STRING) {
				int length = dis.readInt();
				sb.append('[');
				for (int k = 0; k < length; k++) {
					byte bNull = dis.readByte();
					if (bNull == NOT_NULL) {
						String v = dis.readUTF();
						sb.append(String.format("\"%s\"", v));
					} else {
						sb.append("null");
					}
					if (k != length - 1) {
						sb.append(',');
					}
				}
				sb.append(']');
			} else if (type == ARRAY_OBJECT) {
				int length = dis.readInt();
				sb.append('[');
				for (int k = 0; k < length; k++) {
					byte bNull = dis.readByte();
					if (bNull == NOT_NULL) {
						toString(dis, sb, indent + TAB);
						if (k != length - 1) {
							sb.append(",\n");
						}
					} else {
						indent(sb, indent + TAB);
						sb.append("null");
						if (k != length - 1) {
							sb.append(",");
						}
					}
				}
				sb.append(']');
			} else {
				toString(dis, sb, indent + TAB);
			}
			if (j != nFields - 1) {
				sb.append(",\n");
			}
		}
		sb.append('\n');
		indent(sb, indent);
		sb.append('}');
	}

	private static void indent(StringBuilder builder, int indent) {
		for (int i = 0; i < indent; i++) {
			builder.append(' ');
		}
	}

	public static void write(Object object, DataOutputStream out)
			throws IOException {
		try {
			List<Field> fields = getInheritedPrivateFields(object.getClass());
			out.writeByte(fields.size());
			for (int i = 0; i < fields.size(); i++) {
				Field field = fields.get(i);
				field.setAccessible(true);
				Property seq = field.getAnnotation(Property.class);
				String name = "".equals(seq.name()) ? field.getName() : seq
						.name();
				out.writeUTF(name);
				Class<?> type = field.getType();
				if (type == int.class) {
					out.write(INT);
					out.writeInt(field.getInt(object));
					continue;
				} else if (type == short.class) {
					out.write(SHORT);
					out.writeShort(field.getShort(object));
					continue;
				} else if (type == byte.class) {
					out.write(BYTE);
					out.writeByte(field.getByte(object));
					continue;
				} else if (type == long.class) {
					out.write(LONG);
					out.writeLong(field.getLong(object));
					continue;
				} else if (type == float.class) {
					out.write(FLOAT);
					out.writeFloat(field.getFloat(object));
					continue;
				} else if (type == double.class) {
					out.write(DOUBLE);
					out.writeDouble(field.getDouble(object));
					continue;
				} else if (type == boolean.class) {
					out.write(BOOLEAN);
					out.writeBoolean(field.getBoolean(object));
					continue;
				} else if (type == char.class) {
					out.write(CHAR);
					out.writeChar(field.getChar(object));
					continue;
				}
				Object value = field.get(object);
				if (value == null) {
					out.writeByte(NULL);
					continue;
				}
				Class<?> clazz = value.getClass();
				if (clazz.isArray()) {
					Class<?> element = clazz.getComponentType();
					int length = Array.getLength(value);
					if (element == int.class) {
						out.writeByte(ARRAY_INT);
						out.writeInt(length);
						for (int k = 0; k < length; k++) {
							int val = Array.getInt(value, k);
							out.writeInt(val);
						}
					} else if (element == short.class) {
						out.writeByte(ARRAY_SHORT);
						out.writeInt(length);
						for (int k = 0; k < length; k++) {
							short val = Array.getShort(value, k);
							out.writeShort(val);
						}
					} else if (element == byte.class) {
						out.writeByte(ARRAY_BYTE);
						out.writeInt(length);
						for (int k = 0; k < length; k++) {
							byte val = Array.getByte(value, k);
							out.writeByte(val);
						}
					} else if (element == long.class) {
						out.writeByte(ARRAY_LONG);
						out.writeInt(length);
						for (int k = 0; k < length; k++) {
							long val = Array.getLong(value, k);
							out.writeLong(val);
						}
					} else if (element == float.class) {
						out.writeByte(ARRAY_FLOAT);
						out.writeInt(length);
						for (int k = 0; k < length; k++) {
							float val = Array.getFloat(value, k);
							out.writeFloat(val);
						}
					} else if (element == double.class) {
						out.writeByte(ARRAY_DOUBLE);
						out.writeInt(length);
						for (int k = 0; k < length; k++) {
							double val = Array.getDouble(value, k);
							out.writeDouble(val);
						}
					} else if (element == boolean.class) {
						out.writeByte(ARRAY_BOOLEAN);
						out.writeInt(length);
						for (int k = 0; k < length; k++) {
							boolean val = Array.getBoolean(value, k);
							out.writeBoolean(val);
						}
					} else if (element == char.class) {
						out.writeByte(ARRAY_CHAR);
						out.writeInt(length);
						for (int k = 0; k < length; k++) {
							char val = Array.getChar(value, k);
							out.writeChar(val);
						}
					} else if (element == String.class) {
						out.writeByte(ARRAY_STRING);
						out.writeInt(length);
						for (int k = 0; k < length; k++) {
							Object val = Array.get(value, k);
							if (val == null) {
								out.writeByte(NULL);
							} else {
								out.writeByte(NOT_NULL);
								out.writeUTF((String) val);
							}
						}
					} else if (element.isArray()) {
						throw new IllegalArgumentException(
								"Multi dimensions array is not support");
					} else {
						out.writeByte(ARRAY_OBJECT);
						out.writeInt(length);
						for (int k = 0; k < length; k++) {
							Object val = Array.get(value, k);
							if (val == null) {
								out.writeByte(NULL);
							} else {
								out.writeByte(NOT_NULL);
								write(val, out);
							}
						}
					}
				} else if (List.class.isAssignableFrom(type)) {
					ParameterizedType integerListType = (ParameterizedType) field
							.getGenericType();
					Class<?> element = (Class<?>) integerListType
							.getActualTypeArguments()[0];
					List list = (List) value;
					int length = list.size();
					if (element == String.class) {
						out.writeByte(ARRAY_STRING);
						out.writeInt(length);
						for (int k = 0; k < length; k++) {
							Object val = list.get(k);
							if (val == null) {
								out.writeByte(NULL);
							} else {
								out.writeByte(NOT_NULL);
								out.writeUTF((String) val);
							}
						}
					} else {
						out.writeByte(ARRAY_OBJECT);
						out.writeInt(length);
						for (int k = 0; k < length; k++) {
							Object val = list.get(k);
							if (val == null) {
								out.writeByte(NULL);
							} else {
								out.writeByte(NOT_NULL);
								write(val, out);
							}
						}
					}
				} else if (clazz == String.class) {
					out.writeByte(STRING);
					out.writeUTF((String) value);
				} else {
					out.writeByte(OBJECT);
					write(value, out);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Map<Class<?>, List<Field>> caches;

	static {
		caches = new HashMap<Class<?>, List<Field>>();
	}

	private static List<Field> getInheritedPrivateFields(Class<?> type) {
		if (caches.containsKey(type)) {
			return caches.get(type);
		}
		List<Field> result = new ArrayList<Field>();
		Class<?> i = type;
		while (i != null && i != Object.class) {
			Field[] fields = i.getDeclaredFields();
			if (fields != null) {
				for (int j = 0; j < fields.length; j++) {
					Field field = fields[j];
					Property seq = field.getAnnotation(Property.class);
					if (seq != null) {
						String name = "".equals(seq.name()) ? field.getName()
								: seq.name();
						if (name.equals("length")) {
							result.add(0, field);
						} else {
							result.add(field);
						}
					}
				}
			}
			i = i.getSuperclass();
		}
		caches.put(type, result);
		return result;
	}
}
