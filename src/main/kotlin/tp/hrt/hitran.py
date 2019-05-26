import re
import psycopg2
import numpy as np
from psycopg2 import sql
from psycopg2._psycopg import quote_ident
from psycopg2.extras import DictCursor

conn = psycopg2.connect(dbname='hrt', user='postgres',
                        password='postgres', host='localhost')
cursor = conn.cursor(cursor_factory=DictCursor)

row_data_array = []
data_array = []


def get_array_of_data_from_file(path):
    with open(path, 'r', encoding='utf-8') as i_f:
        lines = i_f.read().splitlines()
        pattern = re.compile(
            r'^(.{2})(.{1})(.{12})(.{10})(.{10})(.{5})(.{5})(.{10})(.{4})(.{8})(.{15})(.{15})(.{15})'
            r'(.{15})(.{6})(.{12})(.{1})(.{7})(.{7})')
        for line in lines:
            raw_result = pattern.findall(line)
            result = []
            for ind, val in enumerate(raw_result[0]):
                new_val = re.sub(r'\s+', '', val)
                result.append(new_val)
            row_data_array.append(result)
    return row_data_array


def convert_data_types(arrays):
    for array in arrays:
        values = []
        arr = np.asarray(array)
        values.append(int(arr[0]))
        values.append(int(arr[1]))
        values.append(float(arr[2]))
        values.append(float(arr[3]))
        values.append(float(arr[4]))
        values.append(float(arr[5]))
        values.append(float(arr[6]))
        values.append(float(arr[7]))
        values.append(float(arr[8]))
        values.append(float(arr[9]))
        values.append(arr[10])
        values.append(arr[11])
        values.append(arr[12])
        values.append(arr[13])
        values.append(int(arr[14]))
        values.append(int(arr[15]))
        values.append(arr[16])
        values.append(float(arr[17]))
        values.append(float(arr[18]))
        data_array.append(values)
    return data_array


def insert_data_to_database(path, table_name):
    array = convert_data_types(get_array_of_data_from_file(path))
    # stmt = f"INSERT INTO \"{table_name}\" values (1, 1, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, '1', '1', '1', '1', 1, 1, '1', 1.0, 1.0)"
    # cursor.execute(stmt)
    for data in array:
        # columns = ['mol_id', 'iso_number', 'vacuum_wavenumber', 'intensity', 'einstein_a', 'gamma_air', 'gamma_self',
        #            'lower_state_energy', 'temperature_dependence', 'air_pressure', 'upper_state_global_quanta',
        #            'lower_state_global_quanta', 'upper_state_local_quanta', 'lower_state_local_quanta', 'i_err',
        #            'i_ref', 'flag', 'g_upper', 'g_lower']
        # stmt = sql.SQL('INSERT INTO {} ({}) VALUES ({})').format(
        #     sql.Identifier('34'),
        #     sql.SQL(',').join(map(sql.Identifier, columns)),
        #     sql.SQL(',').join(map(sql.Identifier, data))
        # )
        # cursor.execute(stmt)
        # cursor.execute(
        #     "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) values (%i, %i, %f, %e, %e, %f, %f, %f, %f, %f, %s, %s, %s, %s, %i, %i, %s, %f, %f)" % table_name, columns,
        #     data
        # )
        # columns_tuple = tuple(columns)
        data_tuple = tuple(data)
        stmt = f"INSERT INTO \"{table_name}\" values {data_tuple}"
        cursor.execute(stmt)


insert_data_to_database('files/34_hit08.par', '34')

cursor.close()
conn.close()
