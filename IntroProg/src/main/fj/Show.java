package fj;

import fj.data.Array;
import fj.data.Either;
import fj.data.LazyString;
import fj.data.List;
import fj.data.Natural;
import fj.data.NonEmptyList;
import fj.data.Option;
import fj.data.Stream;
import fj.data.Tree;
import fj.data.Validation;
import fj.data.hlist.HList;
import fj.data.vector.V2;
import fj.data.vector.V3;
import fj.data.vector.V4;
import fj.data.vector.V5;
import fj.data.vector.V6;
import fj.data.vector.V7;
import fj.data.vector.V8;

import java.math.BigDecimal;
import java.math.BigInteger;

import static fj.Function.compose;
import static fj.P.p;
import static fj.Unit.unit;
import static fj.data.Stream.cons;
import static fj.data.Stream.fromString;
import static fj.data.Stream.join;
import static fj.data.Stream.nil;
import static fj.data.Stream.single;

/**
 * Renders an object for display.
 *
 * @version %build.number%<br>
 *          <ul>
 *          <li>$LastChangedRevision: 406 $</li>
 *          <li>$LastChangedDate: 2010-06-05 08:23:15 +1000 (Sat, 05 Jun 2010) $</li>
 *          </ul>
 */
public final class Show<A> {
  private final F<A, Stream<Character>> f;

  private Show(final F<A, Stream<Character>> f) {
    this.f = f;
  }

  /**
   * Maps the given function across this show as a contra-variant functor.
   *
   * @param f The function to map.
   * @return A new show.
   */
  public <B> Show<B> comap(final F<B, A> f) {
    return show(compose(this.f, f));
  }

  /**
   * Returns the display rendering of the given argument.
   *
   * @param a The argument to display.
   * @return The display rendering of the given argument.
   */
  public Stream<Character> show(final A a) {
    return f.f(a);
  }

  /**
   * Returns the display rendering of the given argument.
   *
   * @param a The argument to display.
   * @return The display rendering of the given argument.
   */
  public List<Character> showl(final A a) {
    return show(a).toList();
  }

  /**
   * Returns the display rendering of the given argument as a <code>String</code>.
   *
   * @param a The argument to display.
   * @return The display rendering of the given argument as a <code>String</code>.
   */
  public String showS(final A a) {
    return Stream.asString(show(a));
  }

  /**
   * Returns the transformation equivalent to this show.
   *
   * @return the transformation equivalent to this show.
   */
  public F<A, String> showS_() {
    return new F<A, String>() {
      public String f(final A a) {
        return showS(a);
      }
    };
  }

  /**
   * Returns the transformation equivalent to this show.
   *
   * @return the transformation equivalent to this show.
   */
  public F<A, Stream<Character>> show_() {
    return f;
  }

  /**
   * Prints the given argument to the standard output stream with a new line.
   *
   * @param a The argument to print.
   * @return The unit value.
   */
  public Unit println(final A a) {
    print(a);
    System.out.println();
    return unit();
  }

  /**
   * Prints the given argument to the standard output stream.
   *
   * @param a The argument to print.
   * @return The unit value.
   */
  public Unit print(final A a) {
    final char[] buffer = new char[8192];
    int c = 0;
    for (Stream<Character> cs = show(a); cs.isNotEmpty(); cs = cs.tail()._1()) {
      buffer[c] = cs.head();
      c++;
      if (c == 8192) {
        System.out.print(buffer);
        c = 0;
      }
    }
    System.out.print(Array.copyOfRange(buffer, 0, c));
    return unit();
  }

  /**
   * Prints the given argument to the standard error stream with a new line.
   *
   * @param a The argument to print.
   */
  public void printlnE(final A a) {
    System.err.println(showS(a));
  }

  /**
   * Returns a show instance using the given function.
   *
   * @param f The function to use for the returned show instance.
   * @return A show instance.
   */
  public static <A> Show<A> show(final F<A, Stream<Character>> f) {
    return new Show<A>(f);
  }

  /**
   * Returns a show instance using the given function.
   *
   * @param f The function to use for the returned show instance.
   * @return A show instance.
   */
  public static <A> Show<A> showS(final F<A, String> f) {
    return new Show<A>(new F<A, Stream<Character>>() {
      public Stream<Character> f(final A a) {
        return fromString(f.f(a));
      }
    });
  }

  /**
   * Returns a show instance that uses {@link Object#toString()} to perform the display rendering.
   *
   * @return A show instance that uses {@link Object#toString()} to perform the display rendering.
   */
  public static <A> Show<A> anyShow() {
    return new Show<A>(new F<A, Stream<Character>>() {
      public Stream<Character> f(final A a) {
        return Stream.fromString(a.toString());
      }
    });
  }

  /**
   * A show instance for the <code>boolean</code> type.
   */
  public static final Show<Boolean> booleanShow = anyShow();

  /**
   * A show instance for the <code>byte</code> type.
   */
  public static final Show<Byte> byteShow = anyShow();

  /**
   * A show instance for the <code>char</code> type.
   */
  public static final Show<Character> charShow = anyShow();

  /**
   * A show instance for the <code>double</code> type.
   */
  public static final Show<Double> doubleShow = anyShow();

  /**
   * A show instance for the <code>float</code> type.
   */
  public static final Show<Float> floatShow = anyShow();

  /**
   * A show instance for the <code>int</code> type.
   */
  public static final Show<Integer> intShow = anyShow();

  /**
   * A show instance for the <code>BigInteger</code> type.
   */
  public static final Show<BigInteger> bigintShow = anyShow();

  /**
   * A show instance for the <code>BigDecimal</code> type.
   */
  public static final Show<BigDecimal> bigdecimalShow = anyShow();

  /**
   * A show instance for the <code>long</code> type.
   */
  public static final Show<Long> longShow = anyShow();

  /**
   * A show instance for the <code>short</code> type.
   */
  public static final Show<Short> shortShow = anyShow();

  /**
   * A show instance for the {@link String} type.
   */
  public static final Show<String> stringShow = anyShow();

  /**
   * A show instance for the {@link StringBuffer} type.
   */
  public static final Show<StringBuffer> stringBufferShow = anyShow();

  /**
   * A show instance for the {@link StringBuilder} type.
   */
  public static final Show<StringBuilder> stringBuilderShow = anyShow();

  /**
   * A show instance for the {@link Option} type.
   *
   * @param sa Show for the element of the option.
   * @return A show instance for the {@link Option} type.
   */
  public static <A> Show<Option<A>> optionShow(final Show<A> sa) {
    return new Show<Option<A>>(new F<Option<A>, Stream<Character>>() {
      public Stream<Character> f(final Option<A> o) {
        return o.isNone() ?
               fromString("None") :
               fromString("Some(").append(sa.f.f(o.some())).append(single(')'));
      }
    });
  }

  /**
   * A show instance for the {@link Either} type.
   *
   * @param sa Show for the left side of the {@link Either}.
   * @param sb Show for the right side of the {@link Either}.
   * @return A show instance for the {@link Either} type.
   */
  public static <A, B> Show<Either<A, B>> eitherShow(final Show<A> sa, final Show<B> sb) {
    return new Show<Either<A, B>>(new F<Either<A, B>, Stream<Character>>() {
      public Stream<Character> f(final Either<A, B> e) {
        return e.isLeft() ?
               fromString("Left(").append(sa.f.f(e.left().value())).append(single(')')) :
               fromString("Right(").append(sb.f.f(e.right().value())).append(single(')'));
      }
    });
  }

  /**
   * A show instance for the {@link Validation} type.
   *
   * @param sa Show for the fail side of the {@link Validation}.
   * @param sb Show for the success side of the {@link Validation}.
   * @return A show instance for the {@link Validation} type.
   */
  public static <A, B> Show<Validation<A, B>> validationShow(final Show<A> sa, final Show<B> sb) {
    return new Show<Validation<A, B>>(new F<Validation<A, B>, Stream<Character>>() {
      public Stream<Character> f(final Validation<A, B> v) {
        return v.isFail() ?
               fromString("Fail(").append(sa.f.f(v.fail())).append(single(')')) :
               fromString("Success(").append(sb.f.f(v.success())).append(single(')'));
      }
    });
  }

  /**
   * A show instance for the {@link Stream} type.
   *
   * @param sa Show for the elements of the Stream.
   * @return A show instance for the {@link Stream} type.
   */
  public static <A> Show<List<A>> listShow(final Show<A> sa) {
    return new Show<List<A>>(new F<List<A>, Stream<Character>>() {
      public Stream<Character> f(final List<A> as) {
        return streamShow(sa).show(as.toStream());
      }
    });
  }

  /**
   * A show instance for the {@link NonEmptyList} type.
   *
   * @param sa Show for the elements of the non-empty Stream.
   * @return A show instance for the {@link NonEmptyList} type.
   */
  public static <A> Show<NonEmptyList<A>> nonEmptyListShow(final Show<A> sa) {
    return listShow(sa).comap(NonEmptyList.<A>toList_());
  }

  /**
   * A show instance for the {@link Tree} type.
   *
   * @param sa Show for the elements of the tree.
   * @return A show instance for the {@link Tree} type.
   */
  public static <A> Show<Tree<A>> treeShow(final Show<A> sa) {
    return new Show<Tree<A>>(new F<Tree<A>, Stream<Character>>() {
      public Stream<Character> f(final Tree<A> a) {
        final Stream<Character> b = sa.f.f(a.root())
            .append(p1Show(streamShow(treeShow(sa))).f.f(a.subForest()))
            .snoc(')');
        return cons('(', p(b));
      }
    });
  }

  /**
   * A show instance for the {@link Stream} type.
   *
   * @param sa Show for the elements of the stream.
   * @return A show instance for the {@link Stream} type.
   */
  public static <A> Show<Stream<A>> streamShow(final Show<A> sa) {
    return new Show<Stream<A>>(new F<Stream<A>, Stream<Character>>() {
      public Stream<Character> f(final Stream<A> as) {
        return join(as.map(sa.show_()).intersperse(fromString(",")).cons(fromString("<")).snoc(p(fromString(">"))));
      }
    });
  }

  /**
   * A show instance for the {@link Array} type.
   *
   * @param sa Show for the elements of the array.
   * @return A show instance for the {@link Array} type.
   */
  public static <A> Show<Array<A>> arrayShow(final Show<A> sa) {
    return new Show<Array<A>>(new F<Array<A>, Stream<Character>>() {
      public Stream<Character> f(final Array<A> as) {
        Stream<Character> b = nil();

        for (int i = 0; i < as.length(); i++) {
          b = b.append(sa.f.f(as.get(i)));

          if (i != as.length() - 1)
            b = b.snoc(',');
        }

        b = b.snoc('}');

        return cons('{', p(b));
      }
    });
  }

  /**
   * A show instance for the {@link Class} type.
   *
   * @return A show instance for the {@link Class} type.
   */
  public static <A> Show<Class<A>> classShow() {
    return new Show<Class<A>>(new F<fj.Class<A>, Stream<Character>>() {
      public Stream<Character> f(final Class<A> c) {
        return anyShow().show(c.clas());
      }
    });
  }

  /**
   * A show instance for the {@link P1 tuple-1} type.
   *
   * @param sa Show for the first element of the tuple.
   * @return A show instance for the {@link P1 tuple-1} type.
   */
  public static <A> Show<P1<A>> p1Show(final Show<A> sa) {
    return new Show<P1<A>>(new F<P1<A>, Stream<Character>>() {
      public Stream<Character> f(final P1<A> p) {
        return cons('(', p(sa.show(p._1()))).snoc(')');
      }
    });
  }

  /**
   * A show instance for the {@link P2 tuple-2} type.
   *
   * @param sa Show for the first element of the tuple.
   * @param sb Show for the second element of the tuple.
   * @return A show instance for the {@link P2 tuple-2} type.
   */
  public static <A, B> Show<P2<A, B>> p2Show(final Show<A> sa, final Show<B> sb) {
    return new Show<P2<A, B>>(new F<P2<A, B>, Stream<Character>>() {
      public Stream<Character> f(final P2<A, B> p) {
        return cons('(', p(sa.show(p._1()))).snoc(',').append(sb.show(p._2())).snoc(')');
      }
    });
  }

  /**
   * A show instance for the {@link P3 tuple-3} type.
   *
   * @param sa Show for the first element of the tuple.
   * @param sb Show for the second element of the tuple.
   * @param sc Show for the third element of the tuple.
   * @return A show instance for the {@link P3 tuple-3} type.
   */
  public static <A, B, C> Show<P3<A, B, C>> p3Show(final Show<A> sa, final Show<B> sb, final Show<C> sc) {
    return new Show<P3<A, B, C>>(new F<P3<A, B, C>, Stream<Character>>() {
      public Stream<Character> f(final P3<A, B, C> p) {
        return cons('(', p(sa.show(p._1()))).snoc(',').append(sb.show(p._2())).snoc(',')
            .append(sc.show(p._3())).snoc(')');
      }
    });
  }

  /**
   * A show instance for the {@link P4 tuple-4} type.
   *
   * @param sa Show for the first element of the tuple.
   * @param sb Show for the second element of the tuple.
   * @param sc Show for the third element of the tuple.
   * @param sd Show for the fourth element of the tuple.
   * @return A show instance for the {@link P4 tuple-4} type.
   */
  public static <A, B, C, D> Show<P4<A, B, C, D>> p4Show(final Show<A> sa, final Show<B> sb,
                                                         final Show<C> sc, final Show<D> sd) {
    return new Show<P4<A, B, C, D>>(new F<P4<A, B, C, D>, Stream<Character>>() {
      public Stream<Character> f(final P4<A, B, C, D> p) {
        return cons('(', p(sa.show(p._1()))).snoc(',').append(sb.show(p._2())).snoc(',')
            .append(sc.show(p._3())).snoc(',').append(sd.show(p._4())).snoc(')');
      }
    });
  }

  /**
   * A show instance for the {@link P5 tuple-5} type.
   *
   * @param sa Show for the first element of the tuple.
   * @param sb Show for the second element of the tuple.
   * @param sc Show for the third element of the tuple.
   * @param sd Show for the fourth element of the tuple.
   * @param se Show for the fifth element of the tuple.
   * @return A show instance for the {@link P5 tuple-5} type.
   */
  public static <A, B, C, D, E> Show<P5<A, B, C, D, E>> p5Show(final Show<A> sa, final Show<B> sb,
                                                               final Show<C> sc, final Show<D> sd, final Show<E> se) {
    return new Show<P5<A, B, C, D, E>>(new F<P5<A, B, C, D, E>, Stream<Character>>() {
      public Stream<Character> f(final P5<A, B, C, D, E> p) {
        return cons('(', p(sa.show(p._1()))).snoc(',').append(sb.show(p._2())).snoc(',')
            .append(sc.show(p._3())).snoc(',').append(sd.show(p._4())).snoc(',').append(se.show(p._5())).snoc(')');
      }
    });
  }

  /**
   * A show instance for the {@link P6 tuple-6} type.
   *
   * @param sa Show for the first element of the tuple.
   * @param sb Show for the second element of the tuple.
   * @param sc Show for the third element of the tuple.
   * @param sd Show for the fourth element of the tuple.
   * @param se Show for the fifth element of the tuple.
   * @param sf Show for the sixth element of the tuple.
   * @return A show instance for the {@link P6 tuple-6} type.
   */
  public static <A, B, C, D, E, F$> Show<P6<A, B, C, D, E, F$>> p6Show(final Show<A> sa, final Show<B> sb,
                                                                       final Show<C> sc, final Show<D> sd,
                                                                       final Show<E> se, final Show<F$> sf) {
    return new Show<P6<A, B, C, D, E, F$>>(new F<P6<A, B, C, D, E, F$>, Stream<Character>>() {
      public Stream<Character> f(final P6<A, B, C, D, E, F$> p) {
        return cons('(', p(sa.show(p._1()))).snoc(',').append(sb.show(p._2())).snoc(',')
            .append(sc.show(p._3())).snoc(',').append(sd.show(p._4())).snoc(',')
            .append(se.show(p._5())).snoc(',').append(sf.show(p._6())).snoc(')');
      }
    });
  }

  /**
   * A show instance for the {@link P7 tuple-7} type.
   *
   * @param sa Show for the first element of the tuple.
   * @param sb Show for the second element of the tuple.
   * @param sc Show for the third element of the tuple.
   * @param sd Show for the fourth element of the tuple.
   * @param se Show for the fifth element of the tuple.
   * @param sf Show for the sixth element of the tuple.
   * @param sg Show for the seventh element of the tuple.
   * @return A show instance for the {@link P7 tuple-7} type.
   */
  public static <A, B, C, D, E, F$, G> Show<P7<A, B, C, D, E, F$, G>> p7Show(final Show<A> sa, final Show<B> sb,
                                                                             final Show<C> sc, final Show<D> sd,
                                                                             final Show<E> se, final Show<F$> sf,
                                                                             final Show<G> sg) {
    return new Show<P7<A, B, C, D, E, F$, G>>(new F<P7<A, B, C, D, E, F$, G>, Stream<Character>>() {
      public Stream<Character> f(final P7<A, B, C, D, E, F$, G> p) {
        return cons('(', p(sa.show(p._1()))).snoc(',').append(sb.show(p._2())).snoc(',')
            .append(sc.show(p._3())).snoc(',').append(sd.show(p._4())).snoc(',')
            .append(se.show(p._5())).snoc(',').append(sf.show(p._6())).snoc(',').append(sg.show(p._7())).snoc(')');
      }
    });
  }

  /**
   * A show instance for the {@link P8 tuple-8} type.
   *
   * @param sa Show for the first element of the tuple.
   * @param sb Show for the second element of the tuple.
   * @param sc Show for the third element of the tuple.
   * @param sd Show for the fourth element of the tuple.
   * @param se Show for the fifth element of the tuple.
   * @param sf Show for the sixth element of the tuple.
   * @param sg Show for the seventh element of the tuple.
   * @param sh Show for the eighth element of the tuple.
   * @return A show instance for the {@link P8 tuple-8} type.
   */
  public static <A, B, C, D, E, F$, G, H> Show<P8<A, B, C, D, E, F$, G, H>> p8Show(final Show<A> sa, final Show<B> sb,
                                                                                   final Show<C> sc, final Show<D> sd,
                                                                                   final Show<E> se, final Show<F$> sf,
                                                                                   final Show<G> sg, final Show<H> sh) {
    return new Show<P8<A, B, C, D, E, F$, G, H>>(new F<P8<A, B, C, D, E, F$, G, H>, Stream<Character>>() {
      public Stream<Character> f(final P8<A, B, C, D, E, F$, G, H> p) {
        return cons('(', p(sa.show(p._1()))).snoc(',').append(sb.show(p._2())).snoc(',')
            .append(sc.show(p._3())).snoc(',').append(sd.show(p._4())).snoc(',')
            .append(se.show(p._5())).snoc(',').append(sf.show(p._6())).snoc(',')
            .append(sg.show(p._7())).snoc(',').append(sh.show(p._8())).snoc(')');
      }
    });
  }

  /**
   * A show instance for a vector-2.
   *
   * @param ea A show for the elements of the vector.
   * @return A show instance for a vector-2.
   */
  public static <A> Show<V2<A>> v2Show(final Show<A> ea) {
    return streamShow(ea).comap(V2.<A>toStream_());
  }

  /**
   * A show instance for a vector-3.
   *
   * @param ea A show for the elements of the vector.
   * @return A show instance for a vector-3.
   */
  public static <A> Show<V3<A>> v3Show(final Show<A> ea) {
    return streamShow(ea).comap(V3.<A>toStream_());
  }

  /**
   * A show instance for a vector-4.
   *
   * @param ea A show for the elements of the vector.
   * @return A show instance for a vector-4.
   */
  public static <A> Show<V4<A>> v4Show(final Show<A> ea) {
    return streamShow(ea).comap(V4.<A>toStream_());
  }

  /**
   * A show instance for a vector-5.
   *
   * @param ea A show for the elements of the vector.
   * @return A show instance for a vector-5.
   */
  public static <A> Show<V5<A>> v5Show(final Show<A> ea) {
    return streamShow(ea).comap(V5.<A>toStream_());
  }

  /**
   * A show instance for a vector-6.
   *
   * @param ea A show for the elements of the vector.
   * @return A show instance for a vector-6.
   */
  public static <A> Show<V6<A>> v6Show(final Show<A> ea) {
    return streamShow(ea).comap(V6.<A>toStream_());
  }

  /**
   * A show instance for a vector-7.
   *
   * @param ea A show for the elements of the vector.
   * @return A show instance for a vector-7.
   */
  public static <A> Show<V7<A>> v7Show(final Show<A> ea) {
    return streamShow(ea).comap(V7.<A>toStream_());
  }

  /**
   * A show instance for a vector-8.
   *
   * @param ea A show for the elements of the vector.
   * @return A show instance for a vector-8.
   */
  public static <A> Show<V8<A>> v8Show(final Show<A> ea) {
    return streamShow(ea).comap(V8.<A>toStream_());
  }

  /**
   * A show instance for natural numbers.
   */
  public static final Show<Natural> naturalShow = bigintShow.comap(new F<Natural, BigInteger>() {
    public BigInteger f(final Natural natural) {
      return natural.bigIntegerValue();
    }
  });

  /**
   * A show instance for streams that splits into lines.
   *
   * @param sa A show instance for the elements of a stream.
   * @return A show instance for streams that splits into lines.
   */
  public static <A> Show<Stream<A>> unlineShow(final Show<A> sa) {
    return new Show<Stream<A>>(new F<Stream<A>, Stream<Character>>() {
      public Stream<Character> f(final Stream<A> as) {
        return join(as.map(sa.show_()).intersperse(fromString("\n")));
      }
    });
  }

  /**
   * A show instance for lazy strings.
   */
  public static final Show<LazyString> lazyStringShow = show(new F<LazyString, Stream<Character>>() {
    public Stream<Character> f(final LazyString string) {
      return string.toStream();
    }
  });

  /**
   * A show instance for the empty heterogeneous Stream.
   */
  public static final Show<HList.HNil> HListShow = showS(Function.<HList.HNil, String>constant("Nil"));

  /**
   * A show instance for heterogeneous Streams.
   *
   * @param e A show instance for the first element of the Stream.
   * @param l A show instance for the rest of the Stream.
   * @return a show instance for heterogeneous Streams.
   */
  public static <E, L extends HList<L>> Show<HList.HCons<E, L>> HListShow(final Show<E> e, final Show<L> l) {
    return show(new F<HList.HCons<E, L>, Stream<Character>>() {
      public Stream<Character> f(final HList.HCons<E, L> c) {
        return e.show(c.head()).cons('[').append(l.show(c.tail())).snoc(']');
      }
    });
  }
}
